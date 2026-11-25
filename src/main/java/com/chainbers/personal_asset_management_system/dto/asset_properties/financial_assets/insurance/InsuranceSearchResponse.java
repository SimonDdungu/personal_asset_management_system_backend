package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.PremiumFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public record InsuranceSearchResponse(
        UUID id,
        UUID assetId,
        String assetName,
        String provider,
        BigDecimal premiumAmount,
        PremiumFrequency premiumFrequency,
        LocalDate endDate,
        Currency currency,
        BigDecimal currentValue
) {
}
