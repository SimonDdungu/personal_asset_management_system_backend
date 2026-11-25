package com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.PremiumFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record InsuranceSearchFilter(
        String assetName,
        String provider,
        BigDecimal minPremiumAmount,
        BigDecimal maxPremiumAmount,
        PremiumFrequency premiumFrequency,
        LocalDate minStartDate,
        LocalDate maxStartDate,
        LocalDate minEndDate,
        LocalDate maxEndDate,
        Currency currency,
        BigDecimal minValue,
        BigDecimal maxValue
) {
}
