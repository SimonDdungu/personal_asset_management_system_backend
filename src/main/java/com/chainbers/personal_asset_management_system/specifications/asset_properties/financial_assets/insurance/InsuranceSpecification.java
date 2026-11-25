package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.Insurance;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.PremiumFrequency;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public class InsuranceSpecification {
    public static Specification<Insurance> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Insurance> nameContainsProvider(String provider) {
        return (root, query, cb) -> (provider == null || provider.isBlank())
                ? null
                : cb.like(cb.lower(root.get("provider")), "%" + provider.trim().toLowerCase() + "%");
    }

    public static Specification<Insurance> premiumAmountFrom(BigDecimal minPremiumAmount) {
        return (root, query, cb) -> minPremiumAmount == null ? null : cb.greaterThanOrEqualTo(root.get("premiumAmount"), minPremiumAmount);
    }

    public static Specification<Insurance> premiumAmountTo(BigDecimal maxPremiumAmount) {
        return (root, query, cb) -> maxPremiumAmount == null ? null : cb.lessThanOrEqualTo(root.get("premiumAmount"), maxPremiumAmount);
    }

    public static Specification<Insurance> premiumFrequencyIs(PremiumFrequency premiumFrequency) {
        return (root, query, cb) -> premiumFrequency == null ? null : cb.equal(root.get("premiumFrequency"), premiumFrequency);
    }

    public static Specification<Insurance> startDateFrom(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.greaterThanOrEqualTo(root.get("startDate"), date);
    }

    public static Specification<Insurance> startDateTo(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.lessThanOrEqualTo(root.get("startDate"), date);
    }

    public static Specification<Insurance> endDateFrom(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.greaterThanOrEqualTo(root.get("endDate"), date);
    }

    public static Specification<Insurance> endDateTo(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.lessThanOrEqualTo(root.get("endDate"), date);
    }

    public static Specification<Insurance> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Insurance> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Insurance> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Insurance> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Insurance> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Insurance> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
