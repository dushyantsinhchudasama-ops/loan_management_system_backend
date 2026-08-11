package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.entity.User;

public interface OtpService {

    void createAndSendVerificationOtp(User user);

    void resendVerificationOtp(String email);

    void verifyEmailOtp(String email, String otp);
}
