package com.chainbers.personal_asset_management_system.controller.auth;

import com.chainbers.personal_asset_management_system.auth.JwtAuthenticationFilter;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${app.jwt.cookie-secure:false}")
    private boolean cookieSecure;

    @Value("${app.jwt.expiration-ms:900000}")
    private int expirationMs;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody CreateUserRequest request) {
        User user = authService.register(request);

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getGender()
        );
    }


    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        String token = authService.login(request);

        Cookie cookie = new Cookie(JwtAuthenticationFilter.COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge(expirationMs / 1000);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        return "Login successful";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(JwtAuthenticationFilter.COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        return "Logout successful";
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getGender()
        );
    }

}
