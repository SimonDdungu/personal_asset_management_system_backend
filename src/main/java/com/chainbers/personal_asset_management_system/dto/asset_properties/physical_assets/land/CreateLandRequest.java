package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.land;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.LandSizeUnit;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateLandRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetImageRequest images,

        @Valid
        CreateAssetDocumentRequest documents,

        @NotBlank(message = "Address of land is required.")
        @Size(max = 255, message = "Address of land exceeds limit.")
        String address,

        @Size(max = 255, message = "Description exceeds limit.")
        String description,

        @NotBlank(message = "Plot number is required.")
        @Size(max = 255, message = "Plot number exceeds limit.")
        String plotNumber,

        @NotNull(message = "Land size is required.")
        @Digits(integer = 10, fraction = 2, message = "Land size exceeds limit.")
        @DecimalMin(value = "0.00", message = "Land size cannot be negative.")
        BigDecimal landSize,

        @NotNull(message = "Land size unit is required.")
        LandSizeUnit landSizeUnit
) {
}
