package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.investment;

import java.math.BigDecimal;
import java.util.Currency;

public record InvestmentSearchFilter(
        String assetName,
        Integer minQuantity,
        Integer maxQuantity,
        String institution,
        BigDecimal minInterestRate,
        BigDecimal maxInterestRate,
        String referenceNumber,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
