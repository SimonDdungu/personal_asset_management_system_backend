package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.PropertySizeUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record RealEstateResponse(
        String address,

        String description,

        Integer numberBedrooms,

        Integer numberBathrooms,

        Integer numberUnits,

        BigDecimal propertySize,

        PropertySizeUnit propertySizeUnit,

        Currency constructionCostCurrency,

        BigDecimal constructionCost,

        LocalDate constructionCompletionDate,

        Currency rentalIncomeCurrency,

        BigDecimal rentalIncome,

        Currency currency,

        BigDecimal currentValue
) {
}
