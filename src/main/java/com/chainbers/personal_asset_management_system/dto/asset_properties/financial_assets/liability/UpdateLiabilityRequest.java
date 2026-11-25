package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.liability;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateLiabilityRequest(
        @Size(max = 255, message = "Creditor exceeds limit.")
        String creditor,

        LocalDate dueDate,

        @Size(max = 255, message = "Description exceeds limit.")
        String description
) {
}
