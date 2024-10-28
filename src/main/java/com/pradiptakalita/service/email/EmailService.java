package com.pradiptakalita.service.email;

import com.pradiptakalita.auth.dto.AuthResponseDTO;
import com.pradiptakalita.auth.dto.EmailRequestDTO;
import com.pradiptakalita.auth.dto.VerifyOtpDTO;

import java.util.Random;

public interface EmailService {
    public String verifyEmailAndSendOTP(EmailRequestDTO emailRequestDTO);
    public AuthResponseDTO verifyOTP(VerifyOtpDTO verifyOtpDTO);

}
