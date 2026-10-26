package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.PropertySizeUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record RealEstateSearchFilter(
        String address,


        Integer minNumberBedrooms,
        Integer maxNumberBedrooms,

        Integer minNumberBathrooms,
        Integer maxNumberBathrooms,

        Integer minNumberUnits,
        Integer maxNumberUnits,

        BigDecimal minPropertySize,
        BigDecimal maxPropertySize,

        PropertySizeUnit propertySizeUnit,

        Currency constructionCostCurrency,

        BigDecimal minConstructionCost,
        BigDecimal maxConstructionCost,

        LocalDate constructionCompletionDate,

        Currency rentalIncomeCurrency,

        BigDecimal minRentalIncome,
        BigDecimal maxRentalIncome,

        Currency currency,

        BigDecimal minValue,
        BigDecimal maxValue
) {
}
