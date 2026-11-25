package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.vehicle;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateVehicleRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetImageRequest images,

        @Valid
        CreateAssetDocumentRequest documents,

        @NotBlank(message = "Make is required.")
        @Size(max = 255, message = "Make exceeds limit.")
        String make,

        @NotBlank(message = "Model is required.")
        @Size(max = 255, message = "Model exceeds limit.")
        String model,

        @Min(value = 0, message = "Year cannot be less than 0.")
        int year,

        @NotBlank(message = "Color is required.")
        @Size(max = 255, message = "Color exceeds limit.")
        String color,

        @Min(value = 0, message = "Mileage cannot be less than 0.")
        Integer mileage,

        @Size(max = 255, message = "Registration number exceeds limit.")
        String registrationNumber,

        @Size(max = 255, message = "VIN exceeds limit.")
        String vin
) {
}
