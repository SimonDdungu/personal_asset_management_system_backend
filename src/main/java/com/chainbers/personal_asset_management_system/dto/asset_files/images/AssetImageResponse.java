package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import java.time.Instant;
import java.util.UUID;

public record AssetImageResponse(
        UUID id,

        String imageUrl,

        String originalFileName,

        boolean isPrimary
) {
}
