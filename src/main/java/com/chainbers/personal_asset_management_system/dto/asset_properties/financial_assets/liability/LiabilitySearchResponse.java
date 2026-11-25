package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.liability;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public record LiabilitySearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String creditor,
        LocalDate dueDate,
        Currency currency,
        BigDecimal currentValue
) {
}
