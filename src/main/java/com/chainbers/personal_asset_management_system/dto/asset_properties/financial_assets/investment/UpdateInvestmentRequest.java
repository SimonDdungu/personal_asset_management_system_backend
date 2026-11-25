package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.investment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateInvestmentRequest(
        @Min(value = 0, message = "Quantity cannot be less than 0.")
        Integer quantity,

        @Size(max = 255, message = "Institution exceeds limit.")
        String institution,

        @Digits(integer = 3, fraction = 2, message = "Interest rate exceeds limit.")
        @DecimalMin(value = "0.00", message = "Interest rate cannot be negative.")
        BigDecimal interestRate,

        @Size(max = 255, message = "Reference number exceeds limit.")
        String referenceNumber
) {
}
