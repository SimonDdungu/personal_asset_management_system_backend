package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record LoansSearchFilter(
        String assetName,
        String lender,
        BigDecimal minOriginalAmount,
        BigDecimal maxOriginalAmount,
        BigDecimal minInterestRate,
        BigDecimal maxInterestRate,
        LocalDate minStartDate,
        LocalDate maxStartDate,
        LocalDate minMaturityDate,
        LocalDate maxMaturityDate,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
