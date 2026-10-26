package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.PropertySizeUnit;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record UpdateRealEstateRequest(
        @Size(max = 255, message = "Address of real estate exceeds limit.")
        String address,

        @Size(max = 255, message = "Description exceeds limit.")
        String description,

        @Min(value = 0, message = "Number of Bedrooms cannot be less than 0.")
        Integer numberBedrooms,

        @Min(value = 0, message = "Number of Bathrooms cannot be less than 0.")
        Integer numberBathrooms,

        @Min(value = 0, message = "Number of Units cannot be less than 0.")
        Integer numberUnits,

        @Digits(integer = 10, fraction = 2, message = "Property size exceeds limit.")
        @DecimalMin(value = "0.00", message = "Property size cannot be negative.")
        BigDecimal propertySize,

        PropertySizeUnit propertySizeUnit,

        Currency constructionCostCurrency,

        @Digits(integer = 17, fraction = 2, message = "Construction cost exceeds limit.")
        @DecimalMin(value = "0.00", message = "Construction cost cannot be negative.")
        BigDecimal constructionCost,

        LocalDate constructionCompletionDate,

        Currency rentalIncomeCurrency,

        @Digits(integer = 17, fraction = 2, message = "Rental income exceeds limit.")
        @DecimalMin(value = "0.00", message = "Rental income cannot be negative.")
        BigDecimal rentalIncome,

        Currency currency,

        @Digits(integer = 17, fraction = 2, message = "Current value exceeds limit.")
        @DecimalMin(value = "0.00", message = "Current value cannot be negative.")
        BigDecimal currentValue
) {
}
