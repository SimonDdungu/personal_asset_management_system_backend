package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
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

        Instant createdAt,

        Instant updatedAt
) {
}
