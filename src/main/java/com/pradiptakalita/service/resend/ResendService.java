package com.pradiptakalita.service.resend;

public interface ResendService {
    public String sendWelcomeEmail(String to, String subject, String name);
    public String sendForgotPasswordOTPEmail(String to, String subject, String name,String otp);
}
