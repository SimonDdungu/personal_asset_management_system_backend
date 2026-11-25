package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.liability;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.liability.Liability;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public class LiabilitySpecification {
    public static Specification<Liability> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Liability> nameContainsCreditor(String creditor) {
        return (root, query, cb) -> (creditor == null || creditor.isBlank())
                ? null
                : cb.like(cb.lower(root.get("creditor")), "%" + creditor.trim().toLowerCase() + "%");
    }

    public static Specification<Liability> dueDateFrom(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.greaterThanOrEqualTo(root.get("dueDate"), date);
    }

    public static Specification<Liability> dueDateTo(LocalDate date) {
        return (root, query, cb) -> date == null ? null : cb.lessThanOrEqualTo(root.get("dueDate"), date);
    }

    public static Specification<Liability> nameContainsDescription(String description) {
        return (root, query, cb) -> (description == null || description.isBlank())
                ? null
                : cb.like(cb.lower(root.get("description")), "%" + description.trim().toLowerCase() + "%");
    }

    public static Specification<Liability> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Liability> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Liability> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Liability> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Liability> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Liability> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
