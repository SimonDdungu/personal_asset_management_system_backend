package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.liability;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateLiabilityRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetDocumentRequest documents,

        @NotBlank(message = "Creditor is required.")
        @Size(max = 255, message = "Creditor exceeds limit.")
        String creditor,

        @NotNull(message = "Due date is required.")
        LocalDate dueDate,

        @Size(max = 255, message = "Description exceeds limit.")
        String description
) {
}
