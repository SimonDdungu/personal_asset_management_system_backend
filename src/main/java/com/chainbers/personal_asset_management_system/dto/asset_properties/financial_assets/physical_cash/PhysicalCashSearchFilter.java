package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.physical_cash;

import java.math.BigDecimal;
import java.util.Currency;

public record PhysicalCashSearchFilter(
        String assetName,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
