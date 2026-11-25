package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.investment;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record InvestmentSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        Integer quantity,
        String institution,
        BigDecimal interestRate,
        Currency currency,
        BigDecimal currentValue
) {
}
