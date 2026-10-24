package com.chainbers.personal_asset_management_system.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank
        @Email(message = "Invalid email address")
        @Size(max = 255, message = "Email is too long")
        String email,

        @NotBlank
        @Size(max = 72, message = "Password is too long")
        String password
) {
}
