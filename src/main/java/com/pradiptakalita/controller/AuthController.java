package com.pradiptakalita.controller;

import com.pradiptakalita.auth.dto.AuthResponseDTO;
import com.pradiptakalita.auth.dto.LoginRequestDTO;
import com.pradiptakalita.auth.dto.RefreshTokenRequestDTO;
import com.pradiptakalita.auth.dto.RegisterRequestDTO;
import com.pradiptakalita.auth.entity.RefreshToken;
import com.pradiptakalita.auth.entity.user.User;
import com.pradiptakalita.auth.service.AuthService;
import com.pradiptakalita.auth.service.JwtService;
import com.pradiptakalita.auth.service.RefreshTokenService;
import com.pradiptakalita.service.rateLimiter.RedisRateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final RedisRateLimiterService redisRateLimiterService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService, RedisRateLimiterService redisRateLimiterService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.redisRateLimiterService = redisRateLimiterService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO,
                                                    HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForUnauthenticatedUser(request);
        return ResponseEntity.ok(authService.register(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO,
                                                 HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForUnauthenticatedUser(request);
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO,
                                                        HttpServletRequest request){
        redisRateLimiterService.isRateLimitedForUnauthenticatedUser(request);
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequestDTO.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}
