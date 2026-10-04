package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateAssetDocumentRequest(
            @NotNull(message = "Asset is required!")
            UUID assetId,

            @NotNull(message = "Document Type is required!")
            UUID assetDocumentTypeId,

            @NotBlank(message = "Document location is required!")
            @Size(max = 2048, message = "Document location exceeds limit.")
            String documentUrl,

            @Size(max = 255, message = "Caption exceeds limit.")
            String caption
) {
}
