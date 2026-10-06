package com.chainbers.personal_asset_management_system.dto.assets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAssetsRequest(
        @NotBlank(message = "Name of asset is required!")
        String name,

        String description,

        @NotNull(message = "Asset Category is required!")
        UUID assetCategoryId,

        @NotNull(message = "Acquisition is required!")
        UUID acquisitionId
) {
}
