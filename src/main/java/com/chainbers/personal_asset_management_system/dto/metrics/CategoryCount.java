package com.chainbers.personal_asset_management_system.dto.metrics;

import java.util.Currency;

public record CategoryCount(
        String category,
        Currency currency,
        Long count
) {
}
