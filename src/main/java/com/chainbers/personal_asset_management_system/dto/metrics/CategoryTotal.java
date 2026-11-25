package com.chainbers.personal_asset_management_system.dto.metrics;

import java.math.BigDecimal;

public record CategoryTotal(
        String category,

        BigDecimal total
) {
}
