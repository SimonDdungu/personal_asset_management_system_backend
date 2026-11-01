package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics;

import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record CreateElectronicsRequest(
        @Valid @NotNull
        CreateAssetsRequest asset,

        @NotBlank(message = "Brand is required.") @Size(max = 255, message = "Brand exceeds limit.")
        String brand,

        @Size(max = 255, message = "Model exceeds limit.")
        String model,

        @Size(max = 255, message = "Serial number exceeds limit.")
        String serialNumber,

        @Size(max = 255, message = "Color exceeds limit.")
        String color,

        Integer year
) {
}
