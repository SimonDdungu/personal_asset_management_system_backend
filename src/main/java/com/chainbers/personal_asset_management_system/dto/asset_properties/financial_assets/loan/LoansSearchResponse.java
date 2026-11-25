package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public record LoansSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String lender,
        BigDecimal originalAmount,
        BigDecimal interestRate,
        LocalDate maturityDate,
        Currency currency,
        BigDecimal currentValue
) {
}
