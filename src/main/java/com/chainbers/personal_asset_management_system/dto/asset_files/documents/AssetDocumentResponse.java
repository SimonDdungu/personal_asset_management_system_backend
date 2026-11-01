package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import java.time.Instant;
import java.util.UUID;

public record AssetDocumentResponse(
        UUID id,

        String documentPath
) {
}
