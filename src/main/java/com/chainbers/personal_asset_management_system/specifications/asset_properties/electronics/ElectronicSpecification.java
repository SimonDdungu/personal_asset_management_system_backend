package com.chainbers.personal_asset_management_system.specifications.asset_properties.electronics;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.electronics.Electronics;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicSpecification {
    public static Specification<Electronics> nameContainsAsset(String assetName){
        return (root, query, criteriaBuilder) -> (assetName == null || assetName.isBlank())
                ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("asset").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsBrand(String brandName){
        return (root, query, criteriaBuilder) -> (brandName == null || brandName.isBlank())
                ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + brandName.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsModel(String modelName){
        return (root, query, criteriaBuilder) -> (modelName == null || modelName.isBlank())
                ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("model")), "%" + modelName.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsSerialNumber(String serialNumber){
        return (root, query, criteriaBuilder) -> (serialNumber == null || serialNumber.isBlank())
                ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("serial_number")), "%" + serialNumber.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> nameContainsColor(String color){
        return (root, query, criteriaBuilder) -> (color == null || color.isBlank())
                ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("color")), "%" + color.trim().toLowerCase() + "%");
    }

    public static Specification<Electronics> yearFrom(Integer minYear) {
        return (root, query, criteriaBuilder) -> minYear == null
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("year"), minYear);
    }

    public static Specification<Electronics> yearTo(Integer maxYear) {
        return (root, query, criteriaBuilder) -> maxYear == null
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("year"), maxYear);
    }

    public static Specification<Electronics> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("currentValue"), minValue);
    }

    public static Specification<Electronics> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null
                ? null
                : cb.lessThanOrEqualTo(root.get("currentValue"), maxValue);
    }

    public static Specification<Electronics> currencyIs(String currency) {
        return (root, query, criteriaBuilder) -> (currency == null || currency.isBlank())
        ? null
        : criteriaBuilder.equal(root.get("currency"),  currency.trim().toUpperCase());
    }


    public static Specification<Electronics> belongsTo(UUID userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Electronics> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Electronics> isDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("assets").get("deletedAt"));
    }

}
