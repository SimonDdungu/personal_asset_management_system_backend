package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.vehicle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateVehicleRequest(
        @Size(max = 255, message = "Make exceeds limit.")
        String make,

        @Size(max = 255, message = "Model exceeds limit.")
        String model,

        @Min(value = 0, message = "Year cannot be less than 0.")
        Integer year,

        @Size(max = 255, message = "Color exceeds limit.")
        String color,

        @Min(value = 0, message = "Mileage cannot be less than 0.")
        Integer mileage,

        @Size(max = 255, message = "Registration number exceeds limit.")
        String registrationNumber,

        @Size(max = 255, message = "VIN exceeds limit.")
        String vin
) {
}
