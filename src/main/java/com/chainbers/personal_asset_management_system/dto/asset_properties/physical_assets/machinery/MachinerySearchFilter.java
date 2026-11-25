package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.machinery;

import java.math.BigDecimal;
import java.util.Currency;

public record MachinerySearchFilter(
        String assetName,
        String brand,
        String model,
        String material,
        Integer minYear,
        Integer maxYear,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
