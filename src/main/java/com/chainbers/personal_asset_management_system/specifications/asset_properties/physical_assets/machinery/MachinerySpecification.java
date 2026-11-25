package com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.machinery;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.machinery.Machinery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class MachinerySpecification {
    public static Specification<Machinery> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Machinery> nameContainsBrand(String brand) {
        return (root, query, cb) -> (brand == null || brand.isBlank())
                ? null
                : cb.like(cb.lower(root.get("brand")), "%" + brand.trim().toLowerCase() + "%");
    }

    public static Specification<Machinery> nameContainsModel(String model) {
        return (root, query, cb) -> (model == null || model.isBlank())
                ? null
                : cb.like(cb.lower(root.get("model")), "%" + model.trim().toLowerCase() + "%");
    }

    public static Specification<Machinery> nameContainsMaterial(String material) {
        return (root, query, cb) -> (material == null || material.isBlank())
                ? null
                : cb.like(cb.lower(root.get("material")), "%" + material.trim().toLowerCase() + "%");
    }

    public static Specification<Machinery> yearFrom(Integer minYear) {
        return (root, query, cb) -> minYear == null ? null : cb.greaterThanOrEqualTo(root.get("year"), minYear);
    }

    public static Specification<Machinery> yearTo(Integer maxYear) {
        return (root, query, cb) -> maxYear == null ? null : cb.lessThanOrEqualTo(root.get("year"), maxYear);
    }

    public static Specification<Machinery> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Machinery> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Machinery> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Machinery> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Machinery> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Machinery> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
