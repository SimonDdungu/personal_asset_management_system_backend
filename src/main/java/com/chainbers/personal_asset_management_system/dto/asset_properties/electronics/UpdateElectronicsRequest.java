package com.chainbers.personal_asset_management_system.dto.asset_properties.electronics;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateElectronicsRequest(
        @Size(max = 255, message = "Brand exceeds limit.")
        String brand,

        @Size(max = 255, message = "Model exceeds limit.")
        String model,

        @Size(max = 255, message = "Serial number exceeds limit.")
        String serialNumber,

        @Size(max = 255, message = "Color exceeds limit.")
        String color,

        Integer year,

        @Size(max = 3, message = "Currency cannot exceed 3 characters.")
        String currency,

        @Digits(integer = 17, fraction = 2, message = "Current value exceeds limit.")
        @DecimalMin(value = "0.00", message = "Current value cannot be negative.")
        BigDecimal currentValue
) {
}
