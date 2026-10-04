package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAssetDocumentRequest(
            @NotNull
            UUID assetId,

            @NotNull
            UUID assetDocumentTypeId,

            @NotBlank
            String documentUrl,

            String caption
) {
}
