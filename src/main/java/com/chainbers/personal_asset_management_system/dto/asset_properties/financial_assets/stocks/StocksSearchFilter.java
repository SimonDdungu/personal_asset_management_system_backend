package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.stocks;

import java.math.BigDecimal;
import java.util.Currency;

public record StocksSearchFilter(
        String assetName,
        String tickerSymbol,
        Integer minNumberShares,
        Integer maxNumberShares,
        String brokerage,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
