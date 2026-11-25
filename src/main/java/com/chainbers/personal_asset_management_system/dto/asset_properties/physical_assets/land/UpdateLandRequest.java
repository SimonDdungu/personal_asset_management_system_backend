package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.land;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.LandSizeUnit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateLandRequest(
        @Size(max = 255, message = "Address of land exceeds limit.")
        String address,

        @Size(max = 255, message = "Description exceeds limit.")
        String description,

        @Size(max = 255, message = "Plot number exceeds limit.")
        String plotNumber,

        @Digits(integer = 10, fraction = 2, message = "Land size exceeds limit.")
        @DecimalMin(value = "0.00", message = "Land size cannot be negative.")
        BigDecimal landSize,

        LandSizeUnit landSizeUnit
) {
}
