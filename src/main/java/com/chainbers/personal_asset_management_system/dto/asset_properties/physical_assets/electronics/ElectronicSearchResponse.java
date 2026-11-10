package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record ElectronicSearchResponse(
        UUID id,

        UUID assetId,

        String assetName,

        AssetImageResponse image,

        Currency currency,

        BigDecimal currentValue
) {
}
