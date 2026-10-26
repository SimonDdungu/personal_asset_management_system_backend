package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.bank_account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BankAccountResponse(
        UUID id,

        UUID assetId,

        String assetName,

        String bank,

        String bankAccountNumber,

        BigDecimal interestRate,

        String currency,

        BigDecimal currentBalance,

        BigDecimal previousBalance,

        Instant currentBalanceUpdatedAt,

        Instant createdAt,

        Instant updatedAt
) {
}
