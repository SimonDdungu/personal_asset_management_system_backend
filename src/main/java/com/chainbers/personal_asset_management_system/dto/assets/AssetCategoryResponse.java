package com.chainbers.personal_asset_management_system.dto.assets;

import java.util.UUID;

public record AssetCategoryResponse(
    UUID assetTypeId,

    String assetTypeName,

    String name,

    String description
) {
}
