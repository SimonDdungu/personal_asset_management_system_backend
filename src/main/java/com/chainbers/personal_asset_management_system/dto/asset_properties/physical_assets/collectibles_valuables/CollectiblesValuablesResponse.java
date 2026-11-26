package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.collectibles_valuables;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record CollectiblesValuablesResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String acquisition,
        String assetType,
        String assetCategory,
        String description,
        String creator,
        String serialNumber,
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
