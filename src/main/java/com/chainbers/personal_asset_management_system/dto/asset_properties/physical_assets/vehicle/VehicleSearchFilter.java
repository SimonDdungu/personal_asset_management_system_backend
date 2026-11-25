package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.vehicle;

import java.math.BigDecimal;
import java.util.Currency;

public record VehicleSearchFilter(
        String assetName,
        String make,
        String model,
        Integer minYear,
        Integer maxYear,
        String color,
        Integer minMileage,
        Integer maxMileage,
        String registrationNumber,
        String vin,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
