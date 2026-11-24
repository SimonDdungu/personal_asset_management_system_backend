package com.chainbers.personal_asset_management_system.dto.metrics;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record LargestAsset(
        UUID id,
        String name,
        String category,
        Currency currency,
        BigDecimal currentValue
) {
}
