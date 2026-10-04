package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import jakarta.validation.constraints.NotBlank;

public record UpdateAssetImageRequest(
        @NotBlank(message = "Image location is required!")
        String imageUrl,

        String caption,

        boolean isPrimary
) {
}
