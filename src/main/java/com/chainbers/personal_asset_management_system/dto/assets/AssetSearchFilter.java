package com.chainbers.personal_asset_management_system.dto.assets;

import java.time.Instant;
import java.util.UUID;

public record AssetSearchFilter(

        String name,

        UUID assetCategoryId,

        UUID acquisitionId

) {
}
