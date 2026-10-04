package com.chainbers.personal_asset_management_system.dto.assets;

import java.util.UUID;

public record AssetTypeResponse(
        UUID id,

        String name,

        String description
) {
}
