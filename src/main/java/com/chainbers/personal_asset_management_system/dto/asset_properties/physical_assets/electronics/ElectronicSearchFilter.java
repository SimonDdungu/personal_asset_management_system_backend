package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics;

import java.math.BigDecimal;

public record ElectronicSearchFilter(
        String assetName,

        String brand,

        String model,

        String serialNumber,

        String color,

        Integer minYear,

        Integer maxYear,

        String currency,

        BigDecimal minValue,

        BigDecimal maxValue

        ) {
}
