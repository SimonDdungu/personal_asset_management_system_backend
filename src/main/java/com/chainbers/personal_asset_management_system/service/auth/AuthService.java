package com.chainbers.personal_asset_management_system.service.auth;

import com.chainbers.personal_asset_management_system.auth.JwtService;
import com.chainbers.personal_asset_management_system.dto.user.CreateUserRequest;
import com.chainbers.personal_asset_management_system.dto.user.LoginRequest;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.cookie.name}")
    private String cookieName;

    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @Value("${app.jwt.expiration-ms:900000}")
    private int expirationMs;

    @Value("${app.cookie.same-site}")
    private String sameSite;

    @Transactional
    public User register(CreateUserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,  "An account with this email already exists");
        }

        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setGender(request.gender());
        user.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return user;
    }

    public void setAuthCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, token)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(expirationMs / 1000)
                .sameSite(sameSite)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearAuthCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(0)
                .sameSite(sameSite)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
