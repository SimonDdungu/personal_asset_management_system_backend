package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.bank_account;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateBankAccountRequest(
        @NotNull(message = "Asset is required.")
        UUID assetId,

        @NotBlank(message = "Bank is required.")
        @Size(max = 255, message = "Bank name exceeds limit.")
        String bank,

        @NotNull(message = "Bank account number is required.")
        @Size(max=40, message = "Bank account number exceeds limit.")
        String bankAccountNumber,

        @DecimalMin(value = "0.00", message = "Interest rate cannot be negative.")
        @Digits(integer = 3, fraction = 2, message = "Interest rate exceeds limit.")
        BigDecimal interestRate,

        @NotBlank(message = "Currency is required.") @Size(max = 3, message = "Currency cannot exceed 3 characters.")
        String currency,

        @NotNull(message = "Current Balance is required.")
        @Digits(integer = 17, fraction = 2, message = "Current balance exceeds limit.")
        @DecimalMin(value = "0.00", message = "Current balance cannot be negative.")
        BigDecimal currentBalance,

        @NotNull(message = "Previous Balance is required.")
        @Digits(integer = 17, fraction = 2, message = "Previous balance exceeds limit.")
        @DecimalMin(value = "0.00", message = "Previous balance cannot be negative.")
        BigDecimal previousBalance

) {
}
