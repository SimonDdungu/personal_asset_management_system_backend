package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateAssetImageRequest(
        @NotNull(message = "Asset is required!")
        UUID assetId,

        @NotBlank(message = "Image location is required!")
        @Size(max = 2048, message = "Image location exceeds limit.")
        String imageUrl,

        @Size(max = 255, message = "Caption exceeds limit.")
        String caption,

        boolean isPrimary
        ) {
}
