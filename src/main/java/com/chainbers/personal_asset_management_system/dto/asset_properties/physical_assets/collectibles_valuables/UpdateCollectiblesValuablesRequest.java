package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.collectibles_valuables;

import jakarta.validation.constraints.Size;

public record UpdateCollectiblesValuablesRequest(
        @Size(max = 255, message = "Creator exceeds limit.")
        String creator,

        @Size(max = 255, message = "Serial number exceeds limit.")
        String serialNumber,

        @Size(max = 255, message = "Material exceeds limit.")
        String material,

        Integer year
) {
}
