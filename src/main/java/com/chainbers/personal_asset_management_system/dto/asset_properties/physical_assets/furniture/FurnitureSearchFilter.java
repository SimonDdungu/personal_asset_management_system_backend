package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.furniture;

import java.math.BigDecimal;
import java.util.Currency;

public record FurnitureSearchFilter(
        String assetName,
        String brand,
        String material,
        String description,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
