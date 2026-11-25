package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.furniture;

import jakarta.validation.constraints.Size;

public record UpdateFurnitureRequest(
        @Size(max = 255, message = "Brand exceeds limit.")
        String brand,

        @Size(max = 255, message = "Material exceeds limit.")
        String material,

        @Size(max = 255, message = "Description exceeds limit.")
        String description
) {
}
