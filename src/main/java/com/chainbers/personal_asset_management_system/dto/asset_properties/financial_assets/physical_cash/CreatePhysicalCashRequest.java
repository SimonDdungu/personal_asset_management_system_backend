package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.physical_cash;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreatePhysicalCashRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetDocumentRequest documents
) {
}
