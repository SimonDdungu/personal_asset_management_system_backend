package com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.vehicle;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.vehicle.Vehicle;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class VehicleSpecification {
    public static Specification<Vehicle> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Vehicle> nameContainsMake(String make) {
        return (root, query, cb) -> (make == null || make.isBlank())
                ? null
                : cb.like(cb.lower(root.get("make")), "%" + make.trim().toLowerCase() + "%");
    }

    public static Specification<Vehicle> nameContainsModel(String model) {
        return (root, query, cb) -> (model == null || model.isBlank())
                ? null
                : cb.like(cb.lower(root.get("model")), "%" + model.trim().toLowerCase() + "%");
    }

    public static Specification<Vehicle> yearFrom(Integer minYear) {
        return (root, query, cb) -> minYear == null ? null : cb.greaterThanOrEqualTo(root.get("year"), minYear);
    }

    public static Specification<Vehicle> yearTo(Integer maxYear) {
        return (root, query, cb) -> maxYear == null ? null : cb.lessThanOrEqualTo(root.get("year"), maxYear);
    }

    public static Specification<Vehicle> nameContainsColor(String color) {
        return (root, query, cb) -> (color == null || color.isBlank())
                ? null
                : cb.like(cb.lower(root.get("color")), "%" + color.trim().toLowerCase() + "%");
    }

    public static Specification<Vehicle> mileageFrom(Integer minMileage) {
        return (root, query, cb) -> minMileage == null ? null : cb.greaterThanOrEqualTo(root.get("mileage"), minMileage);
    }

    public static Specification<Vehicle> mileageTo(Integer maxMileage) {
        return (root, query, cb) -> maxMileage == null ? null : cb.lessThanOrEqualTo(root.get("mileage"), maxMileage);
    }

    public static Specification<Vehicle> nameContainsRegistrationNumber(String registrationNumber) {
        return (root, query, cb) -> (registrationNumber == null || registrationNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("registrationNumber")), "%" + registrationNumber.trim().toLowerCase() + "%");
    }

    public static Specification<Vehicle> nameContainsVin(String vin) {
        return (root, query, cb) -> (vin == null || vin.isBlank())
                ? null
                : cb.like(cb.lower(root.get("vin")), "%" + vin.trim().toLowerCase() + "%");
    }

    public static Specification<Vehicle> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Vehicle> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Vehicle> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Vehicle> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Vehicle> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Vehicle> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
