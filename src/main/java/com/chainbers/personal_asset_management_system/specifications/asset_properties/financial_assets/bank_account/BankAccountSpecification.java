package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.bank_account;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.bank_account.BankAccount;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class BankAccountSpecification {
    public static Specification<BankAccount> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<BankAccount> nameContainsBank(String bank) {
        return (root, query, cb) -> (bank == null || bank.isBlank())
                ? null
                : cb.like(cb.lower(root.get("bank")), "%" + bank.trim().toLowerCase() + "%");
    }

    public static Specification<BankAccount> nameContainsBankAccountNumber(String bankAccountNumber) {
        return (root, query, cb) -> (bankAccountNumber == null || bankAccountNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("bankAccountNumber")), "%" + bankAccountNumber.trim().toLowerCase() + "%");
    }

    public static Specification<BankAccount> interestRateFrom(BigDecimal minInterestRate) {
        return (root, query, cb) -> minInterestRate == null ? null : cb.greaterThanOrEqualTo(root.get("interestRate"), minInterestRate);
    }

    public static Specification<BankAccount> interestRateTo(BigDecimal maxInterestRate) {
        return (root, query, cb) -> maxInterestRate == null ? null : cb.lessThanOrEqualTo(root.get("interestRate"), maxInterestRate);
    }

    public static Specification<BankAccount> previousBalanceFrom(BigDecimal minPreviousBalance) {
        return (root, query, cb) -> minPreviousBalance == null ? null : cb.greaterThanOrEqualTo(root.get("previousBalance"), minPreviousBalance);
    }

    public static Specification<BankAccount> previousBalanceTo(BigDecimal maxPreviousBalance) {
        return (root, query, cb) -> maxPreviousBalance == null ? null : cb.lessThanOrEqualTo(root.get("previousBalance"), maxPreviousBalance);
    }

    public static Specification<BankAccount> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<BankAccount> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<BankAccount> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<BankAccount> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<BankAccount> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<BankAccount> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
