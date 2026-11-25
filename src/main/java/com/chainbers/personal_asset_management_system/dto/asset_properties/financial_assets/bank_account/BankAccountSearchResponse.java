package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.bank_account;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String bank,
        BigDecimal interestRate,
        String currency,
        BigDecimal currentBalance
) {
}
