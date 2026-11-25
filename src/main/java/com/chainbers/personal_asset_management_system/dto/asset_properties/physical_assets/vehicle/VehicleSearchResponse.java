package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.vehicle;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record VehicleSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String make,
        String model,
        Integer year,
        String color,
        Integer mileage,
        AssetImageResponse image,
        Currency currency,
        BigDecimal currentValue
) {
}
