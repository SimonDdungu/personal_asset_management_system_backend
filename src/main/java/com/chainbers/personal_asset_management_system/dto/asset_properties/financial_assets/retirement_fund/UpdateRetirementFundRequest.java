package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.retirement_fund;

import jakarta.validation.constraints.Size;

public record UpdateRetirementFundRequest(
        @Size(max = 255, message = "Provider exceeds limit.")
        String provider
) {
}
