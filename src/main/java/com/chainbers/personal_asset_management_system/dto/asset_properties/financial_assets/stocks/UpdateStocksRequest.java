package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.stocks;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateStocksRequest(
        @Size(max = 255, message = "Ticker symbol exceeds limit.")
        String tickerSymbol,

        @Min(value = 0, message = "Number of shares cannot be less than 0.")
        Integer numberShares,

        @Size(max = 255, message = "Brokerage exceeds limit.")
        String brokerage
) {
}
