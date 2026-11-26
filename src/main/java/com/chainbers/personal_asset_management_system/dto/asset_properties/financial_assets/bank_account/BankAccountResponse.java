package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.bank_account;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record BankAccountResponse(
        UUID id,

        UUID assetId,

        String assetName,

        String description,

        String bank,

        String bankAccountNumber,

        BigDecimal interestRate,

        String currency,

        BigDecimal currentBalance,

        BigDecimal previousBalance,

        Instant currentBalanceUpdatedAt,

        List<AssetDocumentUploadResponse> documents,

        Instant createdAt,

        Instant updatedAt
) {
}
