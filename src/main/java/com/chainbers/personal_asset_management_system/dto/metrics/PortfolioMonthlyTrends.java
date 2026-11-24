package com.chainbers.personal_asset_management_system.dto.metrics;

import java.util.Currency;
import java.util.List;

public record PortfolioMonthlyTrends(
        int year,
        Currency currency,
        List<MonthlyPortfolioValue> monthlyValues
) {
}
