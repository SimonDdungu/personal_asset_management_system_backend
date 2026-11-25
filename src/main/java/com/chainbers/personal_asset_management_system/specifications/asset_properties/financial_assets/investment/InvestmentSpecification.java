package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.investment;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.investment.Investment;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class InvestmentSpecification {
    public static Specification<Investment> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Investment> quantityFrom(Integer minQuantity) {
        return (root, query, cb) -> minQuantity == null ? null : cb.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
    }

    public static Specification<Investment> quantityTo(Integer maxQuantity) {
        return (root, query, cb) -> maxQuantity == null ? null : cb.lessThanOrEqualTo(root.get("quantity"), maxQuantity);
    }

    public static Specification<Investment> nameContainsInstitution(String institution) {
        return (root, query, cb) -> (institution == null || institution.isBlank())
                ? null
                : cb.like(cb.lower(root.get("institution")), "%" + institution.trim().toLowerCase() + "%");
    }

    public static Specification<Investment> interestRateFrom(BigDecimal minInterestRate) {
        return (root, query, cb) -> minInterestRate == null ? null : cb.greaterThanOrEqualTo(root.get("interestRate"), minInterestRate);
    }

    public static Specification<Investment> interestRateTo(BigDecimal maxInterestRate) {
        return (root, query, cb) -> maxInterestRate == null ? null : cb.lessThanOrEqualTo(root.get("interestRate"), maxInterestRate);
    }

    public static Specification<Investment> nameContainsReferenceNumber(String referenceNumber) {
        return (root, query, cb) -> (referenceNumber == null || referenceNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("referenceNumber")), "%" + referenceNumber.trim().toLowerCase() + "%");
    }

    public static Specification<Investment> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Investment> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Investment> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Investment> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Investment> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Investment> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
