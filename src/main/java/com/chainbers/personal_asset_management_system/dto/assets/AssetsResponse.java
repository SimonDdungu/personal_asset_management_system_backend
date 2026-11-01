package com.chainbers.personal_asset_management_system.dto.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public record AssetsResponse(
        UUID id,

        String name,

        String description,

        UUID assetCategoryId,

        String assetCategoryName,

        UUID acquisitionId,

        String acquisitionName,

        Currency currency,

        BigDecimal currentValue,

        Instant currentValueUpdatedAt,

        Instant createdAt,

        Instant updatedAt
) {
}
