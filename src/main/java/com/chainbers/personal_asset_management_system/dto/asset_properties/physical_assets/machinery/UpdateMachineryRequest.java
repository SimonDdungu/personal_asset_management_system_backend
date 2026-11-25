package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.machinery;

import jakarta.validation.constraints.Size;

public record UpdateMachineryRequest(
        @Size(max = 255, message = "Brand exceeds limit.")
        String brand,

        @Size(max = 255, message = "Model exceeds limit.")
        String model,

        @Size(max = 255, message = "Material exceeds limit.")
        String material,

        Integer year
) {
}
