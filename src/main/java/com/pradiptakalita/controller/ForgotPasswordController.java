package com.pradiptakalita.controller;


import com.pradiptakalita.auth.dto.AuthResponseDTO;
import com.pradiptakalita.auth.dto.EmailRequestDTO;
import com.pradiptakalita.auth.dto.VerifyOtpDTO;
import com.pradiptakalita.service.email.EmailService;
import com.pradiptakalita.utils.RateLimitUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/forgot-password")
public class ForgotPasswordController {


    private final EmailService emailService;
    private final RateLimitUtil rateLimitUtil;

    public ForgotPasswordController(EmailService emailService, RateLimitUtil rateLimitUtil) {
        this.emailService = emailService;
        this.rateLimitUtil = rateLimitUtil;
    }

    // first verify the email (if it exists then send OTP to that email, if not send back to register page)
    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmailAndSendOTP(@RequestBody @Valid EmailRequestDTO emailRequestDTO,
                                                        HttpServletRequest request){
        rateLimitUtil.isRateLimitedForUnauthenticatedUser(request);
        return  ResponseEntity.ok(emailService.verifyEmailAndSendOTP(emailRequestDTO));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponseDTO> verifyOTP(@RequestBody @Valid VerifyOtpDTO otpDTO,
                                                     HttpServletRequest request){
        rateLimitUtil.isRateLimitedForUnauthenticatedUser(request);
        return ResponseEntity.ok(emailService.verifyOTP(otpDTO));
    }

}
