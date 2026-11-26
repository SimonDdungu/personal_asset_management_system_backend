package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.machinery;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record MachineryResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String acquisition,
        String assetType,
        String assetCategory,
        String description,
        String brand,
        String model,
        String material,
        Integer year,
        Currency currency,
        BigDecimal currentValue,
        Instant currentValueUpdatedAt,
        List<AssetImageUploadResponse> images,
        List<AssetDocumentUploadResponse> documents,
        Instant createdAt,
        Instant updatedAt
) {
}
