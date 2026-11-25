package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.collectibles_valuables;

import java.math.BigDecimal;
import java.util.Currency;

public record CollectiblesValuablesSearchFilter(
        String assetName,
        String creator,
        String serialNumber,
        String material,
        Integer minYear,
        Integer maxYear,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
