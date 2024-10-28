package com.pradiptakalita.auth.service;

import com.pradiptakalita.auth.dto.AuthResponseDTO;
import com.pradiptakalita.auth.dto.LoginRequestDTO;
import com.pradiptakalita.auth.dto.RegisterRequestDTO;
import com.pradiptakalita.auth.entity.user.User;
import com.pradiptakalita.auth.entity.user.UserRole;
import com.pradiptakalita.auth.repository.UserRepository;
import com.pradiptakalita.service.email.EmailService;
import com.pradiptakalita.service.resend.ResendService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final ResendService resendService;

    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO){
        var user = User.builder()
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(UserRole.USER)
                .build();
        try {
            User savedUser = userRepository.save(user);
            resendService.sendWelcomeEmail(savedUser.getEmail(),"WELCOME "+savedUser.getFirstName()+" "+savedUser.getLastName(),registerRequestDTO.getUsername());
            var accessToken = jwtService.generateToken(savedUser);
            var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());
            return AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        } catch (DataIntegrityViolationException e) {
            // Handle unique constraint violation
            throw new RuntimeException("A user with this email already exists.");
        } catch (ConstraintViolationException e) {
            // Handle validation constraint failure
            throw new RuntimeException("User data is invalid.");
        } catch (DataAccessException e) {
            // Handle general database access errors
            throw new RuntimeException("Unable to save user due to database error.");
        }
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequestDTO
                .getEmail())
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with email : " + loginRequestDTO.getEmail()));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequestDTO.getEmail());

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
