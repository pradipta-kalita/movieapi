package com.pradiptakalita.service.email;

import com.pradiptakalita.auth.dto.AuthResponseDTO;
import com.pradiptakalita.auth.dto.EmailRequestDTO;
import com.pradiptakalita.auth.dto.VerifyOtpDTO;
import com.pradiptakalita.auth.entity.ForgotPassword;
import com.pradiptakalita.auth.entity.user.User;
import com.pradiptakalita.auth.repository.ForgotPasswordRepository;
import com.pradiptakalita.auth.repository.UserRepository;
import com.pradiptakalita.auth.service.JwtService;
import com.pradiptakalita.auth.service.RefreshTokenService;
import com.pradiptakalita.service.resend.ResendService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService{

    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final ResendService resendService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public EmailServiceImpl(UserRepository userRepository, ForgotPasswordRepository forgotPasswordRepository, ResendService resendService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.resendService = resendService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public String verifyEmailAndSendOTP(EmailRequestDTO emailRequestDTO) {
        User user = userRepository.findByEmail(emailRequestDTO.getEmail()).orElseThrow(()->
                new UsernameNotFoundException("User not found with email address : "+
                        emailRequestDTO.getEmail()));

        Integer otp = otpGenerator();
        ForgotPassword forgotPasswordObj = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date((System.currentTimeMillis()+100*1000)))
                .user(user)
                .build();

        String resendId = resendService.sendForgotPasswordOTPEmail(
                user.getEmail(),
                "OTP VERIFICATION",
                user.getFirstName()+" "+user.getLastName(),
                otp.toString());
        forgotPasswordRepository.save(forgotPasswordObj);
        return "Email sent for verification";
    }

    @Override
    public AuthResponseDTO verifyOTP(VerifyOtpDTO verifyOtpDTO) {
        User user = userRepository.findByEmail(verifyOtpDTO.getEmail()).orElseThrow(()->
                new UsernameNotFoundException("User not found with email address : "+
                        verifyOtpDTO.getEmail()));

        ForgotPassword forgotPasswordObj = user.getForgotPassword();
        if(forgotPasswordObj == null){
            throw new RuntimeException("The provided OTP or email is incorrect. Please check your details and try again.");
        }
        if(forgotPasswordObj.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPasswordObj.getId());
            // TODO : CREATE A CUSTOM EXCEPTION LATER
            throw new RuntimeException("OTP has expired. Try again later.");
        }

        if(forgotPasswordObj.getAttempts()>=3){
            forgotPasswordRepository.deleteById(forgotPasswordObj.getId());
            // TODO: CREATE A CUSTOM EXCEPTION LATER
            throw new RuntimeException("Too many attempts. Please request a new OTP.");
        }

        if(!forgotPasswordObj.getOtp().equals(verifyOtpDTO.getOtp())){
            forgotPasswordObj.setAttempts(forgotPasswordObj.getAttempts()+1);
            forgotPasswordRepository.save(forgotPasswordObj);
            // TODO: CREATE A CUSTOM EXCEPTION
            throw new RuntimeException("Invalid OTP with email : "+verifyOtpDTO.getEmail());
        }

        forgotPasswordRepository.deleteById(forgotPasswordObj.getId());

        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }


    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000,999_999);
    }
}
