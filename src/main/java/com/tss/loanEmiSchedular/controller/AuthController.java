package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.JwtResponseDto;
import com.tss.loanEmiSchedular.dto.LoginRequestDto;
import com.tss.loanEmiSchedular.dto.OtpResendRequestDto;
import com.tss.loanEmiSchedular.dto.OtpVerificationRequestDto;
import com.tss.loanEmiSchedular.dto.RegistrationRequestDto;
import com.tss.loanEmiSchedular.dto.UserResponseDto;
import com.tss.loanEmiSchedular.service.AuthService;
import com.tss.loanEmiSchedular.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    // Open to everyone. Always creates a BORROWER ("User") account.
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegistrationRequestDto request) {
        UserResponseDto response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Open to everyone — issues a JWT for any of the 3 roles based on
    // whatever role is stored on that user's account.
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        JwtResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody OtpVerificationRequestDto request) {
        otpService.verifyEmailOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/resend-email")
    public ResponseEntity<String> resendEmail(@Valid @RequestBody OtpResendRequestDto request) {
        otpService.resendVerificationOtp(request.getEmail());
        return ResponseEntity.ok("OTP resend initiated");
    }
}
