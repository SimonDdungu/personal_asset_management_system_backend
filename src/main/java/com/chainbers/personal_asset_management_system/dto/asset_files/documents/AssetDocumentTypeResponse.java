package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import java.util.UUID;

public record AssetDocumentTypeResponse(
        UUID id,

        String name
) {
}
