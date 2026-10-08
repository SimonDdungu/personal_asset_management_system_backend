package com.chainbers.personal_asset_management_system.dto.asset_properties.electronics;

import java.math.BigDecimal;
import java.time.Instant;
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

        String currency,

        BigDecimal currentValue,

        Instant currentValueUpdatedAt,

        Instant createdAt,

        Instant updatedAt
) {
}
