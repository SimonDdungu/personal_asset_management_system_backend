package com.chainbers.personal_asset_management_system.auth;

import com.chainbers.personal_asset_management_system.repository.user.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String COOKIE_NAME = "access_token";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = extractToken(request);

            if (token != null) {
                try {
                    if (jwtService.isValid(token)) {
                        UUID userId = jwtService.extractUserId(token);

                        userRepository.findById(userId).ifPresent(user -> {
                            var authentication = new UsernamePasswordAuthenticationToken(user, null,
                                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                                    );

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
                    }
                } catch (JwtException | IllegalArgumentException exception) {
                    SecurityContextHolder.clearContext();
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) return cookie.getValue();
        }

        return null;
    }
}