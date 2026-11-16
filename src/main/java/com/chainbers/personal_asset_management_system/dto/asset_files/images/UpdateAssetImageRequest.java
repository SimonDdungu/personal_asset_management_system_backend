package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateAssetImageRequest(
        @PositiveOrZero(message = "Primary image is invalid")
        Integer primaryImageIndex
) {
}
