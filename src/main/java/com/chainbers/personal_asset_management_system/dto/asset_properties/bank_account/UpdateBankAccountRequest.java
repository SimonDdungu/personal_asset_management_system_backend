package com.chainbers.personal_asset_management_system.dto.asset_properties.bank_account;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateBankAccountRequest(
        @Size(max = 255, message = "Bank name exceeds limit.")
        String bank,

        @Size(max=40, message = "Bank account number exceeds limit.")
        String bankAccountNumber,

        @DecimalMin(value = "0.00", message = "Interest rate cannot be negative.")
        @Digits(integer = 3, fraction = 2, message = "Interest rate exceeds limit.")
        BigDecimal interestRate,

        @Size(max = 3, message = "Currency cannot exceed 3 characters.")
        String currency,

        @Digits(integer = 17, fraction = 2, message = "Current balance exceeds limit.")
        @DecimalMin(value = "0.00", message = "Current balance cannot be negative.")
        BigDecimal currentBalance,

        @Digits(integer = 17, fraction = 2, message = "Previous balance exceeds limit.")
        @DecimalMin(value = "0.00", message = "Previous balance cannot be negative.")
        BigDecimal previousBalance
) {
}
