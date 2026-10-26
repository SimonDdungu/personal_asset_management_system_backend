package com.chainbers.personal_asset_management_system.dto.assets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateAssetsRequest(
        @NotBlank(message = "Name of asset is required!")
        @Size(max = 255, message = "Name of asset exceeds limit.")
        String name,

        @Size(max = 255, message = "Asset description exceeds limit.")
        String description,

        @NotNull(message = "Asset Category is required!")
        UUID assetCategoryId,

        @NotNull(message = "Acquisition is required!")
        UUID acquisitionId
) {
}
