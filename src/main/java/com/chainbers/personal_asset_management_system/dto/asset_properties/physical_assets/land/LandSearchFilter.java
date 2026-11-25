package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.land;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.LandSizeUnit;

import java.math.BigDecimal;
import java.util.Currency;

public record LandSearchFilter(
        String assetName,
        String address,
        String description,
        String plotNumber,
        BigDecimal minLandSize,
        BigDecimal maxLandSize,
        LandSizeUnit landSizeUnit,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
