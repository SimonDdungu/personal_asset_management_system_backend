package com.chainbers.personal_asset_management_system.service.auth;

import com.chainbers.personal_asset_management_system.auth.JwtService;
import com.chainbers.personal_asset_management_system.dto.user.CreateUserRequest;
import com.chainbers.personal_asset_management_system.dto.user.LoginRequest;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return jwtService.generateToken(user);
    }
}
