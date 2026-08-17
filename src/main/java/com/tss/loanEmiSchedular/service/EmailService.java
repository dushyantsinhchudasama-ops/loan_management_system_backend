package com.tss.loanEmiSchedular.service;

public interface EmailService {

    void sendOtpEmail(String email, String otp);

    void sendEmiOverdueEmail(String email, Long emiId);

    void sendPaymentReminderEmail(String email, Long emiId);


    void sendEmiPaidOnTimeEmail(String email, Long emiId);

    void sendEmiPaidAfterOverdueEmail(String email, Long emiId);
}