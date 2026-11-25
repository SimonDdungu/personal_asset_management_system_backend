package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.loan;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateLoansRequest(
        @Valid @NotNull(message = "Asset is required.")
        CreateAssetsRequest asset,

        @Valid
        CreateAssetDocumentRequest documents,

        @NotBlank(message = "Lender is required.")
        @Size(max = 255, message = "Lender exceeds limit.")
        String lender,

        @NotNull(message = "Original amount is required.")
        @Digits(integer = 17, fraction = 2, message = "Original amount exceeds limit.")
        @DecimalMin(value = "0.00", message = "Original amount cannot be negative.")
        BigDecimal originalAmount,

        @Digits(integer = 3, fraction = 2, message = "Interest rate exceeds limit.")
        @DecimalMin(value = "0.00", message = "Interest rate cannot be negative.")
        BigDecimal interestRate,

        @NotNull(message = "Start date is required.")
        LocalDate startDate,

        LocalDate maturityDate
) {
}
