package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.entity.Otp;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.exception.OtpMaxAttemptsExceededException;
import com.tss.loanEmiSchedular.exception.UserApiException;
import com.tss.loanEmiSchedular.repository.OtpRepository;
import com.tss.loanEmiSchedular.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${otp.expiry-minutes:5}")
    private int otpExpiryMinutes;

    @Value("${otp.max-attempts:3}")
    private int maxAttempts;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public void createAndSendVerificationOtp(User user) {
        invalidateActiveOtps(user.getEmail());

        String otp = generateOtp();
        Otp otpEntity = new Otp();
        otpEntity.setUser(user);
        otpEntity.setEmail(user.getEmail());
        otpEntity.setOtpHash(passwordEncoder.encode(otp));
        otpEntity.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpiryMinutes));
        otpEntity.setAttemptCount(0);
        otpEntity.setVerified(false);
        otpEntity.setInvalidated(false);

        otpRepository.save(otpEntity);
        emailService.sendOtpEmail(user.getEmail(), otp);
    }

    @Override
    public void resendVerificationOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserApiException("No account found with this email"));

        if (Boolean.TRUE.equals(user.isEmailVerified())) {
            throw new UserApiException("Email is already verified");
        }

        createAndSendVerificationOtp(user);
    }

    @Override
    public void verifyEmailOtp(String email, String otp) {
        Otp otpEntity = otpRepository
                .findTopByEmailAndIsVerifiedFalseAndIsInvalidatedFalseOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new UserApiException("OTP not found or already verified. Request a new code."));

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpEntity.setInvalidated(true);
            otpRepository.save(otpEntity);
            throw new UserApiException("OTP has expired. Please request a new one.");
        }

        if (otpEntity.getAttemptCount() >= maxAttempts) {
            otpEntity.setInvalidated(true);
            otpRepository.save(otpEntity);
            throw new OtpMaxAttemptsExceededException("Maximum OTP verification attempts exceeded");
        }

        boolean matches = passwordEncoder.matches(otp, otpEntity.getOtpHash());
        if (!matches) {
            otpEntity.setAttemptCount(otpEntity.getAttemptCount() + 1);
            if (otpEntity.getAttemptCount() >= maxAttempts) {
                otpEntity.setInvalidated(true);
                otpRepository.save(otpEntity);
                throw new OtpMaxAttemptsExceededException("Maximum OTP verification attempts exceeded");
            }
            otpRepository.save(otpEntity);
            throw new UserApiException("Invalid OTP. You have " + (maxAttempts - otpEntity.getAttemptCount()) + " attempts remaining.");
        }

        otpEntity.setVerified(true);
        otpRepository.save(otpEntity);

        User user = otpEntity.getUser();
        if (user == null) {
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserApiException("User not found for provided email"));
        }

        user.setEmailVerified(true);
        userRepository.save(user);
    }

    private void invalidateActiveOtps(String email) {
        List<Otp> activeOtps = otpRepository.findAllByEmailAndIsVerifiedFalseAndIsInvalidatedFalse(email);
        if (!activeOtps.isEmpty()) {
            activeOtps.forEach(otp -> otp.setInvalidated(true));
            otpRepository.saveAll(activeOtps);
        }
    }

    private String generateOtp() {
        int value = secureRandom.nextInt(900_000) + 100_000;
        return String.valueOf(value);
    }
}
