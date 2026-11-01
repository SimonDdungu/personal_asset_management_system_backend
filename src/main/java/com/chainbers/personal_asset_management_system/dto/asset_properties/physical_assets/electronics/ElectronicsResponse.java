package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record ElectronicsResponse(
        UUID id,

        UUID assetId,

        String assetName,

        String brand,

        String model,

        String serialNumber,

        String color,

        Integer year,

        Currency currency,

        BigDecimal currentValue,

        Instant currentValueUpdatedAt,

        List<AssetImageResponse> images,

        Instant createdAt,

        Instant updatedAt
) {
}
