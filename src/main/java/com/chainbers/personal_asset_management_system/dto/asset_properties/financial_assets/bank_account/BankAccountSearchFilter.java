package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.bank_account;

import java.math.BigDecimal;

public record BankAccountSearchFilter(
        String assetName,
        String bank,
        String bankAccountNumber,
        BigDecimal minInterestRate,
        BigDecimal maxInterestRate,
        String currency,
        BigDecimal minValue,
        BigDecimal maxValue,
        BigDecimal minPreviousBalance,
        BigDecimal maxPreviousBalance
) {
}
