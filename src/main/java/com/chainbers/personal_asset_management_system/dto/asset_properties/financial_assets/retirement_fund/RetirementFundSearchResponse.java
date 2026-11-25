package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.retirement_fund;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record RetirementFundSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String provider,
        Currency currency,
        BigDecimal currentValue
) {
}
