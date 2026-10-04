package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateAssetDocumentRequest(
        UUID documentTypeId,

        @Size(max = 255, message = "Caption cannot be more than 255 characters")
        String caption
) {
}
