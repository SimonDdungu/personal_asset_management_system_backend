package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.liability;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record LiabilitySearchFilter(
        String assetName,
        String creditor,
        LocalDate minDueDate,
        LocalDate maxDueDate,
        String description,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
