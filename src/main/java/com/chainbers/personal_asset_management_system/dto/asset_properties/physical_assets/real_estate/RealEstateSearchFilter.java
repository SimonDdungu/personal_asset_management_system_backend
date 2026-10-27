package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.PropertySizeUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record RealEstateSearchFilter(
        String assetName,
        String address,

        Integer minNumberBedrooms,
        Integer maxNumberBedrooms,

        Integer minNumberBathrooms,
        Integer maxNumberBathrooms,

        Integer minNumberUnits,
        Integer maxNumberUnits,

        BigDecimal minPropertySize,
        BigDecimal maxPropertySize,

        String propertySizeUnit,

        String constructionCostCurrency,

        BigDecimal minConstructionCost,
        BigDecimal maxConstructionCost,

        LocalDate minConstructionCompletionDate,
        LocalDate maxConstructionCompletionDate,

        String rentalIncomeCurrency,

        BigDecimal minRentalIncome,
        BigDecimal maxRentalIncome,

        String currency,

        BigDecimal minValue,
        BigDecimal maxValue
) {
}
