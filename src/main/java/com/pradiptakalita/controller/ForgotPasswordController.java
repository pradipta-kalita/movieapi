package com.pradiptakalita.controller;


import com.pradiptakalita.auth.dto.AuthResponseDTO;
import com.pradiptakalita.auth.dto.EmailRequestDTO;
import com.pradiptakalita.auth.dto.VerifyOtpDTO;
import com.pradiptakalita.service.email.EmailService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/forgot-password")
public class ForgotPasswordController {


    private final EmailService emailService;

    public ForgotPasswordController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public String getHere(){
        return "IT IS HITTING GET";
    }
    // first verify the email (if it exists then send OTP to that email, if not send back to register page)
    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmailAndSendOTP(@RequestBody @Valid EmailRequestDTO emailRequestDTO){
        System.out.println("IT IS HITTING IT");
        return  ResponseEntity.ok(emailService.verifyEmailAndSendOTP(emailRequestDTO));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponseDTO> verifyOTP(@RequestBody @Valid VerifyOtpDTO otpDTO){
        return ResponseEntity.ok(emailService.verifyOTP(otpDTO));
    }

//    @PostMapping("/change-password")
}
