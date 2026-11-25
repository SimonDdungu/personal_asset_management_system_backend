package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.PremiumFrequency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateInsuranceRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetDocumentRequest documents,

        @NotBlank(message = "Provider is required.")
        @Size(max = 255, message = "Provider exceeds limit.")
        String provider,

        @NotNull(message = "Premium amount is required.")
        @Digits(integer = 17, fraction = 2, message = "Premium amount exceeds limit.")
        @DecimalMin(value = "0.00", message = "Premium amount cannot be negative.")
        BigDecimal premiumAmount,

        @NotNull(message = "Premium frequency is required.")
        PremiumFrequency premiumFrequency,

        @NotNull(message = "Start date is required.")
        LocalDate startDate,

        @NotNull(message = "End date is required.")
        LocalDate endDate
) {
}
