package com.chainbers.personal_asset_management_system.dto.user;

import com.chainbers.personal_asset_management_system.entity.user.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank
        @Size(max = 100, message = "First name exceeds limit")
        String firstName,

        @NotBlank
        @Size(max = 100, message = "Last name exceeds limit")
        String lastName,

        @NotBlank
        @Email(message = "Invalid email address")
        @Size(max = 255, message = "Email is too long")
        String email,

        @NotBlank
        @Size(min = 8, max = 72, message = "Password is too long")
        String password,

        @Size(max = 30, message = "Phone number is too long")
        String phoneNumber,

        @NotNull(message = "Invalid gender")
        Gender gender
) {
}
