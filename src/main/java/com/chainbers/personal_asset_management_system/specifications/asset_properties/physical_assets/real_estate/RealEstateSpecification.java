package com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.RealEstate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class RealEstateSpecification {
    public static Specification<RealEstate> nameContainsAsset(String assetName){
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("asset").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<RealEstate> nameContainsAddress(String address){
        return (root, query, cb) -> (address == null || address.isBlank())
                ? null
                : cb.like(cb.lower(root.get("address")), "%" + address.trim().toLowerCase() + "%");
    }

    public static Specification<RealEstate> bedroomsFrom(Integer minBedrooms) {
        return (root, query, cb) -> minBedrooms == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("number_bedrooms"), minBedrooms);
    }

    public static Specification<RealEstate> bedroomsTo(Integer maxBedrooms) {
        return (root, query, cb) -> maxBedrooms == null
                ? null
                : cb.lessThanOrEqualTo(root.get("number_bedrooms"), maxBedrooms);
    }

    public static Specification<RealEstate> bathroomsFrom(Integer minBathrooms) {
        return (root, query, cb) -> minBathrooms == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("number_bathrooms"), minBathrooms);
    }

    public static Specification<RealEstate> bathroomsTo(Integer maxBathrooms) {
        return (root, query, cb) -> maxBathrooms == null
                ? null
                : cb.lessThanOrEqualTo(root.get("number_bathrooms"), maxBathrooms);
    }

    public static Specification<RealEstate> numberUnitsFrom(Integer minUnits) {
        return (root, query, cb) -> minUnits == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("number_units"), minUnits);
    }

    public static Specification<RealEstate> numberUnitsTo(Integer maxUnits) {
        return (root, query, cb) -> maxUnits == null
                ? null
                : cb.lessThanOrEqualTo(root.get("number_units"), maxUnits);
    }

    public static Specification<RealEstate> propertySizeFrom(BigDecimal minPropertySize) {
        return (root, query, cb) -> minPropertySize == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("property_size"), minPropertySize);
    }

    public static Specification<RealEstate> propertySizeTo(BigDecimal maxPropertySize) {
        return (root, query, cb) -> maxPropertySize == null
                ? null
                : cb.lessThanOrEqualTo(root.get("property_size"), maxPropertySize);
    }

    public static Specification<RealEstate> propertySizeUnit(String propertySizeUnit) {
        return (root, query, cb) -> (propertySizeUnit == null || propertySizeUnit.isBlank())
                ? null
                : cb.equal(root.get("property_size_unit"),  propertySizeUnit.trim().toUpperCase());
    }

    public static Specification<RealEstate> constructionCostFrom(BigDecimal minConstructionCost) {
        return (root, query, cb) -> minConstructionCost == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("construction_cost"), minConstructionCost);
    }

    public static Specification<RealEstate> constructionCostTo(BigDecimal maxConstructionCost) {
        return (root, query, cb) -> maxConstructionCost == null
                ? null
                : cb.lessThanOrEqualTo(root.get("construction_cost"), maxConstructionCost);
    }

    public static Specification<RealEstate> constructionCurrencyIs(String constructionCurrency) {
        return (root, query, cb) -> (constructionCurrency == null || constructionCurrency.isBlank())
                ? null
                : cb.equal(root.get("construction_currency"),  constructionCurrency.trim().toUpperCase());
    }

    public static Specification<RealEstate> constructionCompletionDateFrom(LocalDate date) {
        return (root, query, cb) -> date == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("construction_completion_date"), date);
    }

    public static Specification<RealEstate> constructionCompletionDateTo(LocalDate date) {
        return (root, query, cb) ->  date == null
                ? null
                : cb.lessThanOrEqualTo(root.get("construction_completion_date"), date);
    }

    public static Specification<RealEstate> rentalIncomeFrom(BigDecimal minRentalIncome) {
        return (root, query, cb) -> minRentalIncome == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("rental_income"), minRentalIncome);
    }

    public static Specification<RealEstate> rentalIncomeTo(BigDecimal maxRentalIncome) {
        return (root, query, cb) -> maxRentalIncome == null
                ? null
                : cb.lessThanOrEqualTo(root.get("rental_income"), maxRentalIncome);
    }

    public static Specification<RealEstate> rentalCurrencyIs(String rentalCurrency) {
        return (root, query, cb) -> (rentalCurrency == null || rentalCurrency.isBlank())
                ? null
                : cb.equal(root.get("rental_income_currency"),  rentalCurrency.trim().toUpperCase());
    }


    public static Specification<RealEstate> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("current_value"), minValue);
    }

    public static Specification<RealEstate> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null
                ? null
                : cb.lessThanOrEqualTo(root.get("current_value"), maxValue);
    }

    public static Specification<RealEstate> currencyIs(String currency) {
        return (root, query, cb) -> (currency == null || currency.isBlank())
                ? null
                : cb.equal(root.get("currency"),  currency.trim().toUpperCase());
    }


    public static Specification<RealEstate> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<RealEstate> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<RealEstate> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }


}
