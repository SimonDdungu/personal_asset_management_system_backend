package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.PremiumFrequency;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record InsuranceResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String acquisition,
        String assetType,
        String assetCategory,
        String provider,
        BigDecimal premiumAmount,
        PremiumFrequency premiumFrequency,
        LocalDate startDate,
        LocalDate endDate,
        Currency currency,
        BigDecimal currentValue,
        Instant currentValueUpdatedAt,
        List<AssetDocumentUploadResponse> documents,
        Instant createdAt,
        Instant updatedAt
) {
}
