package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.loan;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.loan.Loans;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public class LoansSpecification {
    public static Specification<Loans> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Loans> nameContainsLender(String lender) {
        return (root, query, cb) -> (lender == null || lender.isBlank())
                ? null
                : cb.like(cb.lower(root.get("lender")), "%" + lender.trim().toLowerCase() + "%");
    }

    public static Specification<Loans> originalAmountFrom(BigDecimal minOriginalAmount) {
        return (root, query, cb) -> minOriginalAmount == null ? null : cb.greaterThanOrEqualTo(root.get("originalAmount"), minOriginalAmount);
    }

    public static Specification<Loans> originalAmountTo(BigDecimal maxOriginalAmount) {
        return (root, query, cb) -> maxOriginalAmount == null ? null : cb.lessThanOrEqualTo(root.get("originalAmount"), maxOriginalAmount);
    }

    public static Specification<Loans> interestRateFrom(BigDecimal minInterestRate) {
        return (root, query, cb) -> minInterestRate == null ? null : cb.greaterThanOrEqualTo(root.get("interestRate"), minInterestRate);
    }

    public static Specification<Loans> interestRateTo(BigDecimal maxInterestRate) {
        return (root, query, cb) -> maxInterestRate == null ? null : cb.lessThanOrEqualTo(root.get("interestRate"), maxInterestRate);
    }

    public static Specification<Loans> startDateFrom(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.greaterThanOrEqualTo(root.get("startDate"), date);
    }

    public static Specification<Loans> startDateTo(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.lessThanOrEqualTo(root.get("startDate"), date);
    }

    public static Specification<Loans> maturityDateFrom(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.greaterThanOrEqualTo(root.get("maturityDate"), date);
    }

    public static Specification<Loans> maturityDateTo(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.lessThanOrEqualTo(root.get("maturityDate"), date);
    }

    public static Specification<Loans> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Loans> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Loans> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Loans> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Loans> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Loans> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
