package com.chainbers.personal_asset_management_system.dto.asset_properties.bank_account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateBankAccountRequest(
        @NotNull(message = "Asset is required!")
        UUID assetId,

        @NotBlank(message = "Bank is required!")
        String bank,

        @NotNull(message = "Bank account number is required!")
        Integer bankAccountNumber,

        BigDecimal interestRate,

        @NotBlank(message = "Currency is required!") @Size(max = 3, message = "Currency can not be more than 3 characters")
        String currency,

        @NotNull(message = "Current Balance is required!")
        BigDecimal currentBalance,

        @NotNull(message = "Previous Balance is required!")
        BigDecimal previousBalance

) {
}
