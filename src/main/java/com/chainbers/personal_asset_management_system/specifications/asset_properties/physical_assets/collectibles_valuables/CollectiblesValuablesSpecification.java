package com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.collectibles_valuables;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.collectibles_valuables.CollectiblesValuables;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class CollectiblesValuablesSpecification {
    public static Specification<CollectiblesValuables> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<CollectiblesValuables> nameContainsCreator(String creator) {
        return (root, query, cb) -> (creator == null || creator.isBlank())
                ? null
                : cb.like(cb.lower(root.get("creator")), "%" + creator.trim().toLowerCase() + "%");
    }

    public static Specification<CollectiblesValuables> nameContainsSerialNumber(String serialNumber) {
        return (root, query, cb) -> (serialNumber == null || serialNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("serialNumber")), "%" + serialNumber.trim().toLowerCase() + "%");
    }

    public static Specification<CollectiblesValuables> nameContainsMaterial(String material) {
        return (root, query, cb) -> (material == null || material.isBlank())
                ? null
                : cb.like(cb.lower(root.get("material")), "%" + material.trim().toLowerCase() + "%");
    }

    public static Specification<CollectiblesValuables> yearFrom(Integer minYear) {
        return (root, query, cb) -> minYear == null ? null : cb.greaterThanOrEqualTo(root.get("year"), minYear);
    }

    public static Specification<CollectiblesValuables> yearTo(Integer maxYear) {
        return (root, query, cb) -> maxYear == null ? null : cb.lessThanOrEqualTo(root.get("year"), maxYear);
    }

    public static Specification<CollectiblesValuables> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<CollectiblesValuables> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<CollectiblesValuables> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<CollectiblesValuables> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<CollectiblesValuables> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<CollectiblesValuables> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
