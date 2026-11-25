package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.savings;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateSavingsRequest(
        @Size(max = 255, message = "Institution exceeds limit.")
        String institution,

        @Size(max = 255, message = "Account number exceeds limit.")
        String accountNumber,

        @Digits(integer = 3, fraction = 2, message = "Interest rate exceeds limit.")
        @DecimalMin(value = "0.00", message = "Interest rate cannot be negative.")
        BigDecimal interestRate,

        LocalDate maturityDate
) {
}
