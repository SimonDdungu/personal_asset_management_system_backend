package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.savings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public record SavingsSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String institution,
        BigDecimal interestRate,
        LocalDate maturityDate,
        Currency currency,
        BigDecimal currentValue
) {
}
