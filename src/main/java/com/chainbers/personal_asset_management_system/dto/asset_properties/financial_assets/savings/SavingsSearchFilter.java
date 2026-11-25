package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.savings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record SavingsSearchFilter(
        String assetName,
        String institution,
        String accountNumber,
        BigDecimal minInterestRate,
        BigDecimal maxInterestRate,
        LocalDate minMaturityDate,
        LocalDate maxMaturityDate,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
