package com.chainbers.personal_asset_management_system.dto.user;

import com.chainbers.personal_asset_management_system.entity.user.Gender;

import java.util.UUID;

public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Gender gender
) {
}
