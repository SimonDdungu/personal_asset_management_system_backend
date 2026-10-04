package com.chainbers.personal_asset_management_system.dto.assets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssetsRequest(
        @NotBlank
        String name,

        String description,

        @NotNull
        UUID assetCategoryId,

        @NotNull
        UUID acquisitionId
) {
}
