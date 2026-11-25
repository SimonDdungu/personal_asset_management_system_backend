package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.furniture;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFurnitureRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetImageRequest images,

        @Valid
        CreateAssetDocumentRequest documents,

        @Size(max = 255, message = "Brand exceeds limit.")
        String brand,

        @Size(max = 255, message = "Material exceeds limit.")
        String material,

        @Size(max = 255, message = "Description exceeds limit.")
        String description
) {
}
