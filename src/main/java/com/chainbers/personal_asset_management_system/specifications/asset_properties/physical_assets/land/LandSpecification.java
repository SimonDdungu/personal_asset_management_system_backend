package com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.land;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.Land;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.LandSizeUnit;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class LandSpecification {
    public static Specification<Land> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Land> nameContainsAddress(String address) {
        return (root, query, cb) -> (address == null || address.isBlank())
                ? null
                : cb.like(cb.lower(root.get("address")), "%" + address.trim().toLowerCase() + "%");
    }

    public static Specification<Land> nameContainsDescription(String description) {
        return (root, query, cb) -> (description == null || description.isBlank())
                ? null
                : cb.like(cb.lower(root.get("description")), "%" + description.trim().toLowerCase() + "%");
    }

    public static Specification<Land> nameContainsPlotNumber(String plotNumber) {
        return (root, query, cb) -> (plotNumber == null || plotNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("plotNumber")), "%" + plotNumber.trim().toLowerCase() + "%");
    }

    public static Specification<Land> landSizeFrom(BigDecimal minLandSize) {
        return (root, query, cb) -> minLandSize == null ? null : cb.greaterThanOrEqualTo(root.get("landSize"), minLandSize);
    }

    public static Specification<Land> landSizeTo(BigDecimal maxLandSize) {
        return (root, query, cb) -> maxLandSize == null ? null : cb.lessThanOrEqualTo(root.get("landSize"), maxLandSize);
    }

    public static Specification<Land> landSizeUnitIs(LandSizeUnit landSizeUnit) {
        return (root, query, cb) -> landSizeUnit == null ? null : cb.equal(root.get("landSizeUnit"), landSizeUnit);
    }

    public static Specification<Land> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Land> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Land> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Land> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Land> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Land> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
