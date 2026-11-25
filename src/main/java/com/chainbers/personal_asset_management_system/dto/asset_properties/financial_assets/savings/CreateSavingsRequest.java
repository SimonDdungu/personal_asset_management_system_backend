package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.savings;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateSavingsRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetDocumentRequest documents,

        @NotBlank(message = "Institution is required.")
        @Size(max = 255, message = "Institution exceeds limit.")
        String institution,

        @NotBlank(message = "Account number is required.")
        @Size(max = 255, message = "Account number exceeds limit.")
        String accountNumber,

        @Digits(integer = 3, fraction = 2, message = "Interest rate exceeds limit.")
        @DecimalMin(value = "0.00", message = "Interest rate cannot be negative.")
        BigDecimal interestRate,

        LocalDate maturityDate
) {
}
