package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.savings;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.savings.Savings;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public class SavingsSpecification {
    public static Specification<Savings> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Savings> nameContainsInstitution(String institution) {
        return (root, query, cb) -> (institution == null || institution.isBlank())
                ? null
                : cb.like(cb.lower(root.get("institution")), "%" + institution.trim().toLowerCase() + "%");
    }

    public static Specification<Savings> nameContainsAccountNumber(String accountNumber) {
        return (root, query, cb) -> (accountNumber == null || accountNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("accountNumber")), "%" + accountNumber.trim().toLowerCase() + "%");
    }

    public static Specification<Savings> interestRateFrom(BigDecimal minInterestRate) {
        return (root, query, cb) -> minInterestRate == null ? null : cb.greaterThanOrEqualTo(root.get("interestRate"), minInterestRate);
    }

    public static Specification<Savings> interestRateTo(BigDecimal maxInterestRate) {
        return (root, query, cb) -> maxInterestRate == null ? null : cb.lessThanOrEqualTo(root.get("interestRate"), maxInterestRate);
    }

    public static Specification<Savings> maturityDateFrom(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.greaterThanOrEqualTo(root.get("maturityDate"), date);
    }

    public static Specification<Savings> maturityDateTo(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.lessThanOrEqualTo(root.get("maturityDate"), date);
    }

    public static Specification<Savings> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Savings> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Savings> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Savings> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Savings> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Savings> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
