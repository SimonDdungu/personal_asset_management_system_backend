package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.PremiumFrequency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateInsuranceRequest(
        @Size(max = 255, message = "Provider exceeds limit.")
        String provider,

        @Digits(integer = 17, fraction = 2, message = "Premium amount exceeds limit.")
        @DecimalMin(value = "0.00", message = "Premium amount cannot be negative.")
        BigDecimal premiumAmount,

        PremiumFrequency premiumFrequency,

        LocalDate startDate,

        LocalDate endDate
) {
}
