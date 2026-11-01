package com.chainbers.personal_asset_management_system.dto.asset_files.images;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AssetImageUploadResponse(
        UUID id,
        UUID assetId,
        String caption,
        Instant uploadedAt,
        Instant updatedAt,
        List<AssetImageResponse> images
) {
}
