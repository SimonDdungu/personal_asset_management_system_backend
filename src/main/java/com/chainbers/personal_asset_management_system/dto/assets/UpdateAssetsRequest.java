package com.chainbers.personal_asset_management_system.dto.assets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateAssetsRequest(
        @Size(max = 255, message = "Asset name exceeds limit.")
        String name,

        @Size(max = 255, message = "Asset description exceeds limit.")
        String description,

        UUID assetCategoryId,

        UUID acquisitionId
) {
}
