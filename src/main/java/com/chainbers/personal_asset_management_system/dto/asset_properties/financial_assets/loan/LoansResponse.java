package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.loan;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record LoansResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String acquisition,
        String assetType,
        String assetCategory,
        String description,
        String lender,
        BigDecimal originalAmount,
        BigDecimal interestRate,
        LocalDate startDate,
        LocalDate maturityDate,
        Currency currency,
        BigDecimal currentValue,
        Instant currentValueUpdatedAt,
        List<AssetDocumentUploadResponse> documents,
        Instant createdAt,
        Instant updatedAt
) {
}
