package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record CreateAssetImageRequest(
        @NotNull(message = "Asset is required!")
        UUID assetId,

        @NotEmpty(message = "At least one image is required!")
        List<@NotNull(message = "Image file is required!") MultipartFile> images,

        @Size(max = 255, message = "Caption exceeds limit.")
        String caption,

        @PositiveOrZero(message = "Primary image is invalid")
        Integer primaryImageIndex
        ) {
}
