package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.stocks;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateStocksRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetDocumentRequest documents,

        @NotBlank(message = "Ticker symbol is required.")
        @Size(max = 255, message = "Ticker symbol exceeds limit.")
        String tickerSymbol,

        @NotNull(message = "Number of shares is required.")
        @Min(value = 0, message = "Number of shares cannot be less than 0.")
        Integer numberShares,

        @NotBlank(message = "Brokerage is required.")
        @Size(max = 255, message = "Brokerage exceeds limit.")
        String brokerage
) {
}
