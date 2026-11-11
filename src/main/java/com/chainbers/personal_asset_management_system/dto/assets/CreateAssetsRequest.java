package com.chainbers.personal_asset_management_system.dto.assets;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record CreateAssetsRequest(
        @NotBlank(message = "Name of asset is required!")
        @Size(max = 255, message = "Name of asset exceeds limit.")
        String name,

        @Size(max = 255, message = "Asset description exceeds limit.")
        String description,

        @NotNull(message = "Asset Type is required!")
        String assetType,

        @NotNull(message = "Asset Category is required!")
        String assetCategory,

        @NotNull(message = "Acquisition is required!")
        String acquisition,

        @NotNull(message = "Currency is required.")
        Currency currency,

        @NotNull(message = "Current value is required.")
        @Digits(integer = 17, fraction = 2, message = "Current value exceeds limit.")
        @DecimalMin(value = "0.00", message = "Current value cannot be negative.")
        BigDecimal currentValue
) {
}
