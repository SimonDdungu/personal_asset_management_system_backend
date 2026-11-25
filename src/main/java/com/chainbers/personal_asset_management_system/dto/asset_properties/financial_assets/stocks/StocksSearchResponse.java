package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.stocks;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record StocksSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String tickerSymbol,
        Integer numberShares,
        String brokerage,
        Currency currency,
        BigDecimal currentValue
) {
}
