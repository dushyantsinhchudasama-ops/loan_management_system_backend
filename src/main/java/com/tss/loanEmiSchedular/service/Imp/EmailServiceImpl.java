package com.tss.loanEmiSchedular.service.Imp;

import com.tss.loanEmiSchedular.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("EMI Scheduler - Email Verification OTP");
        message.setText("Hello,\n\n" +
                "Your OTP for email verification is: " + otp + "\n\n" +
                "This OTP is valid for 5 minutes.\n\n" +
                "Please do not share this OTP with anyone.\n\n" +
                "Regards,\n" +
                "EMI Scheduler Team");

        mailSender.send(message);
    }

    @Override
    public void sendEmiOverdueEmail(String email, Long emiId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("EMI Scheduler - Payment Overdue");
        message.setText("Hello,\n\n" +
                "Your EMI (ID: " + emiId + ") is overdue. A penalty has been applied.\n" +
                "Please make the payment as soon as possible to avoid further charges.\n\n" +
                "Regards,\n" +
                "EMI Scheduler Team");

        mailSender.send(message);
    }

    @Override
    public void sendPaymentReminderEmail(String email, Long emiId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("EMI Scheduler - Upcoming Payment Reminder");
        message.setText("Hello,\n\n" +
                "This is a reminder that your EMI (ID: " + emiId + ") is due soon.\n" +
                "Please ensure timely payment to avoid penalties.\n\n" +
                "Regards,\n" +
                "EMI Scheduler Team");

        mailSender.send(message);
    }

    @Override
    public void sendEmiPaidOnTimeEmail(String email, Long emiId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("EMI Scheduler - Payment Received");
        message.setText("Hello,\n\n" +
                "We have received your payment for EMI (ID: " + emiId + ") on time.\n" +
                "Thank you for your timely payment!\n\n" +
                "Regards,\n" +
                "EMI Scheduler Team");

        mailSender.send(message);
    }

    @Override
    public void sendEmiPaidAfterOverdueEmail(String email, Long emiId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("EMI Scheduler - Overdue Payment Received");
        message.setText("Hello,\n\n" +
                "We have received your payment for EMI (ID: " + emiId + "), which was overdue.\n" +
                "A penalty was applied for the delay. Please try to pay future EMIs on time to avoid extra charges.\n\n" +
                "Regards,\n" +
                "EMI Scheduler Team");

        mailSender.send(message);
    }
}