package com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.stocks;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.stocks.Stocks;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class StocksSpecification {
    public static Specification<Stocks> nameContainsAsset(String assetName) {
        return (root, query, cb) -> (assetName == null || assetName.isBlank())
                ? null
                : cb.like(cb.lower(root.get("assets").get("name")), "%" + assetName.trim().toLowerCase() + "%");
    }

    public static Specification<Stocks> nameContainsTickerSymbol(String tickerSymbol) {
        return (root, query, cb) -> (tickerSymbol == null || tickerSymbol.isBlank())
                ? null
                : cb.like(cb.lower(root.get("tickerSymbol")), "%" + tickerSymbol.trim().toLowerCase() + "%");
    }

    public static Specification<Stocks> numberSharesFrom(Integer minNumberShares) {
        return (root, query, cb) -> minNumberShares == null ? null : cb.greaterThanOrEqualTo(root.get("numberShares"), minNumberShares);
    }

    public static Specification<Stocks> numberSharesTo(Integer maxNumberShares) {
        return (root, query, cb) -> maxNumberShares == null ? null : cb.lessThanOrEqualTo(root.get("numberShares"), maxNumberShares);
    }

    public static Specification<Stocks> nameContainsBrokerage(String brokerage) {
        return (root, query, cb) -> (brokerage == null || brokerage.isBlank())
                ? null
                : cb.like(cb.lower(root.get("brokerage")), "%" + brokerage.trim().toLowerCase() + "%");
    }

    public static Specification<Stocks> currentValueFrom(BigDecimal minValue) {
        return (root, query, cb) -> minValue == null ? null : cb.greaterThanOrEqualTo(root.get("assets").get("currentValue"), minValue);
    }

    public static Specification<Stocks> currentValueTo(BigDecimal maxValue) {
        return (root, query, cb) -> maxValue == null ? null : cb.lessThanOrEqualTo(root.get("assets").get("currentValue"), maxValue);
    }

    public static Specification<Stocks> currencyIs(Currency currency) {
        return (root, query, cb) -> currency == null ? null : cb.equal(root.get("assets").get("currency"), currency);
    }

    public static Specification<Stocks> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("assets").get("user").get("id"), userId);
    }

    public static Specification<Stocks> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("assets").get("deletedAt"));
    }

    public static Specification<Stocks> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("assets").get("deletedAt"));
    }
}
