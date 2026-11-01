package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics;

import java.math.BigDecimal;
import java.util.Currency;

public record ElectronicSearchFilter(
        String assetName,

        String brand,

        String model,

        String serialNumber,

        String color,

        Integer minYear,

        Integer maxYear,

        Currency currency,

        BigDecimal minValue,

        BigDecimal maxValue

        ) {
}
