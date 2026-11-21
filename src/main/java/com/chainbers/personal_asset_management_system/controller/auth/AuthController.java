package com.chainbers.personal_asset_management_system.controller.auth;

import com.chainbers.personal_asset_management_system.auth.JwtAuthenticationFilter;
import com.chainbers.personal_asset_management_system.auth.JwtService;
import com.chainbers.personal_asset_management_system.dto.user.CreateUserRequest;
import com.chainbers.personal_asset_management_system.dto.user.LoginRequest;
import com.chainbers.personal_asset_management_system.dto.user.UserResponse;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @Value("${app.jwt.cookie-secure:false}")
    private boolean cookieSecure;

    @Value("${app.jwt.expiration-ms:900000}")
    private int expirationMs;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody CreateUserRequest request,  HttpServletResponse response) {
        User user = authService.register(request);
        String token = jwtService.generateToken(user);
        authService.setAuthCookie(response, token);

        return new UserResponse(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getGender()
        );
    }


    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        User user = authService.login(request);
        String token = jwtService.generateToken(user);
        authService.setAuthCookie(response, token);

        return new UserResponse(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getGender()
        );
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        authService.clearAuthCookie(response);

        return "Logout successful";
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getGender()
        );
    }
}
