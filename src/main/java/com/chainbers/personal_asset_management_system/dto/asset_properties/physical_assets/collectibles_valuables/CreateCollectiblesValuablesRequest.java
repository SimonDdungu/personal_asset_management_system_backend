package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.collectibles_valuables;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCollectiblesValuablesRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetImageRequest images,

        @Valid
        CreateAssetDocumentRequest documents,

        @Size(max = 255, message = "Creator exceeds limit.")
        String creator,

        @Size(max = 255, message = "Serial number exceeds limit.")
        String serialNumber,

        @Size(max = 255, message = "Material exceeds limit.")
        String material,

        Integer year
) {
}
