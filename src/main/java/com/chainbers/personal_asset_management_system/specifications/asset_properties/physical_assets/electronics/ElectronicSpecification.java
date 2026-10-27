package com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.electronics;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.electronics.Electronics;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicSpecification {
    public static Specification<Electronics> nameContainsAsset(String assetName){
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("asset").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsBrand(String brandName){
        return (root, query, cb) -> (brandName == null || brandName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("brand")), "%" + brandName.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsModel(String modelName){
        return (root, query, cb) -> (modelName == null || modelName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("model")), "%" + modelName.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsSerialNumber(String serialNumber){
        return (root, query, cb) -> (serialNumber == null || serialNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("serial_number")), "%" + serialNumber.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsColor(String color){
        return (root, query, cb) -> (color == null || color.isBlank())
                ? null
                : cb.like(cb.lower(root.get("color")), "%" + color.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> yearFrom(Integer minYear) {
        return (root, query, cb) -> minYear == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("year"), minYear);
    }

    public static Specification<Electronics> yearTo(Integer maxYear) {
        return (root, query, cb) -> maxYear == null
                ? null
                : cb.lessThanOrEqualTo(root.get("year"), maxYear);
    }

    public static Specification<Electronics> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("current_value"), minValue);
    }

    public static Specification<Electronics> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null
                ? null
                : cb.lessThanOrEqualTo(root.get("current_value"), maxValue);
    }

    public static Specification<Electronics> currencyIs(String currency) {
        return (root, query, cb) -> (currency == null || currency.isBlank())
        ? null
        : cb.equal(root.get("currency"),  currency.trim().toUpperCase());
    }


    public static Specification<Electronics> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Electronics> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Electronics> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }

}
