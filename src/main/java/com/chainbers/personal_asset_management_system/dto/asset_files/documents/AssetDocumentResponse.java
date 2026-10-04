package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import java.time.Instant;
import java.util.UUID;

public record AssetDocumentResponse(
        UUID id,

        UUID assetId,

        UUID assetDocumentTypeId,

        String documentUrl,

        String caption,

        Instant uploadedAt
) {
}
