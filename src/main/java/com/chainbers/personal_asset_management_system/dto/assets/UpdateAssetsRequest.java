package com.chainbers.personal_asset_management_system.dto.assets;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record UpdateAssetsRequest(
        @Size(max = 255, message = "Asset name exceeds limit.")
        String name,

        @Size(max = 255, message = "Asset description exceeds limit.")
        String description,

        UUID assetCategoryId,

        UUID acquisitionId,

        Currency currency,

        @Digits(integer = 17, fraction = 2, message = "Current value exceeds limit.")
        @DecimalMin(value = "0.00", message = "Current value cannot be negative.")
        BigDecimal currentValue
) {
}
