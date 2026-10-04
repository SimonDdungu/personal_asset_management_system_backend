package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAssetImageRequest(
        @NotNull(message = "Asset is required!")
        UUID assetId,

        @NotBlank(message = "Image location is required!")
        String imageUrl,

        String caption,

        boolean isPrimary
        ) {
}
