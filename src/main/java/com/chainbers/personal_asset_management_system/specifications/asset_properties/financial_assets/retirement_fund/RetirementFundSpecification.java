package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.retirement_fund;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.retirement_fund.RetirementFund;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class RetirementFundSpecification {
    public static Specification<RetirementFund> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<RetirementFund> nameContainsProvider(String provider) {
        return (root, query, cb) -> (provider == null || provider.isBlank())
                ? null
                : cb.like(cb.lower(root.get("provider")), "%" + provider.trim().toLowerCase() + "%");
    }

    public static Specification<RetirementFund> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<RetirementFund> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<RetirementFund> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<RetirementFund> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<RetirementFund> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<RetirementFund> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
