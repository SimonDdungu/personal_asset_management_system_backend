package com.chainbers.personal_asset_management_system.dto.metrics;

import java.math.BigDecimal;
import java.util.Currency;

public record CurrencyAverage(
        Currency currency,
        BigDecimal average
) {
}
