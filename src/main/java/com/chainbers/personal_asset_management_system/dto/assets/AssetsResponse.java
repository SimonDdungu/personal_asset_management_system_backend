package com.chainbers.personal_asset_management_system.dto.assets;

import java.time.Instant;
import java.util.UUID;

public record AssetsResponse(
        UUID id,

        String name,

        String description,

        UUID assetCategoryId,

        String assetCategoryName,

        UUID acquisitionId,

        String acquisitionName,

        Instant createdAt,

        Instant updatedAt
) {
}
