package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.land;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.LandSizeUnit;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record LandSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String address,
        String plotNumber,
        BigDecimal landSize,
        LandSizeUnit landSizeUnit,
        AssetImageResponse image,
        Currency currency,
        BigDecimal currentValue
) {
}
