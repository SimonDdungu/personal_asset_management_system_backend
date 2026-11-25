package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.physical_cash;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record PhysicalCashSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        Currency currency,
        BigDecimal currentValue
) {
}
