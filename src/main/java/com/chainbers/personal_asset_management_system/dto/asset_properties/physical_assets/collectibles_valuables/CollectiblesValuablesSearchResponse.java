package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.collectibles_valuables;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record CollectiblesValuablesSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String creator,
        String serialNumber,
        String material,
        Integer year,
        AssetImageResponse image,
        Currency currency,
        BigDecimal currentValue
) {
}
