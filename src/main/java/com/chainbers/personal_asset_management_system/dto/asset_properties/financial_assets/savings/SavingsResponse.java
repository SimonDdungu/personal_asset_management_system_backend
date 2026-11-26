package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.savings;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record SavingsResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String acquisition,
        String assetType,
        String assetCategory,
        String description,
        String institution,
        String accountNumber,
        BigDecimal interestRate,
        LocalDate maturityDate,
        Currency currency,
        BigDecimal currentValue,
        Instant currentValueUpdatedAt,
        List<AssetDocumentUploadResponse> documents,
        Instant createdAt,
        Instant updatedAt
) {
}
