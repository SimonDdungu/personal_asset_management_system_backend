package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAssetImageRequest(
        @Size(max = 255, message = "Caption exceeds limit.")
        String caption,

        boolean isPrimary
) {
}
