package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.loan;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateLoansRequest(
        @Size(max = 255, message = "Lender exceeds limit.")
        String lender,

        @Digits(integer = 17, fraction = 2, message = "Original amount exceeds limit.")
        @DecimalMin(value = "0.00", message = "Original amount cannot be negative.")
        BigDecimal originalAmount,

        @Digits(integer = 3, fraction = 2, message = "Interest rate exceeds limit.")
        @DecimalMin(value = "0.00", message = "Interest rate cannot be negative.")
        BigDecimal interestRate,

        LocalDate startDate,

        LocalDate maturityDate
) {
}
