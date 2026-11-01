package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AssetDocumentUploadResponse(
        UUID id,
        UUID assetId,
        UUID assetDocumentTypeId,
        String assetDocumentTypeName,
        String caption,
        Instant uploadedAt,
        Instant updatedAt,
        List<AssetDocumentResponse> documents
) {
}
