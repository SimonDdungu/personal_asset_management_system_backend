package com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.furniture;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.furniture.Furniture;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class FurnitureSpecification {
    public static Specification<Furniture> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Furniture> nameContainsBrand(String brand) {
        return (root, query, cb) -> (brand == null || brand.isBlank())
                ? null
                : cb.like(cb.lower(root.get("brand")), "%" + brand.trim().toLowerCase() + "%");
    }

    public static Specification<Furniture> nameContainsMaterial(String material) {
        return (root, query, cb) -> (material == null || material.isBlank())
                ? null
                : cb.like(cb.lower(root.get("material")), "%" + material.trim().toLowerCase() + "%");
    }

    public static Specification<Furniture> nameContainsDescription(String description) {
        return (root, query, cb) -> (description == null || description.isBlank())
                ? null
                : cb.like(cb.lower(root.get("description")), "%" + description.trim().toLowerCase() + "%");
    }

    public static Specification<Furniture> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Furniture> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Furniture> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Furniture> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Furniture> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Furniture> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
