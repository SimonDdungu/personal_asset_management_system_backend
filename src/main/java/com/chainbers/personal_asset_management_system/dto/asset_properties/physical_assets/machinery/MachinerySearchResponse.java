package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.machinery;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record MachinerySearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String brand,
        String model,
        String material,
        Integer year,
        AssetImageResponse image,
        Currency currency,
        BigDecimal currentValue
) {
}
