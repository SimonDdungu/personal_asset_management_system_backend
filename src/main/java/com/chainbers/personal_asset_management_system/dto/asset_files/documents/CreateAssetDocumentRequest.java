package com.chainbers.personal_asset_management_system.dto.asset_files.documents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record CreateAssetDocumentRequest(
            @NotNull(message = "Asset is required!")
            UUID assetId,

            @NotNull(message = "Document Type is required!")
            UUID assetDocumentTypeId,

            @NotEmpty(message = "At least one document is required!")
            List<@NotNull(message = "Document file is required!") MultipartFile> documents,

            @Size(max = 255, message = "Caption exceeds limit.")
            String caption
) {
}
