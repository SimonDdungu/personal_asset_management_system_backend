package com.chainbers.personal_asset_management_system.dto.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public record AssetSearchFilter(

        String name,

        UUID assetCategoryId,

        UUID acquisitionId,

        Currency currency,

        BigDecimal minValue,

        BigDecimal maxValue,

        Instant currentValueUpdatedAt
) {
}
