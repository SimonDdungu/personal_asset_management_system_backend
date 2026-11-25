package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.furniture;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record FurnitureSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String brand,
        String material,
        AssetImageResponse image,
        Currency currency,
        BigDecimal currentValue
) {
}
