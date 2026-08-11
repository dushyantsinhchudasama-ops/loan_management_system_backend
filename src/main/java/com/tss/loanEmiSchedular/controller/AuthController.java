package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.request.OtpResendRequestDto;
import com.tss.loanEmiSchedular.dto.request.OtpVerificationRequestDto;
import com.tss.loanEmiSchedular.dto.response.JwtResponseDto;
import com.tss.loanEmiSchedular.dto.request.LoginRequestDto;
import com.tss.loanEmiSchedular.dto.request.RegistrationRequestDto;
import com.tss.loanEmiSchedular.dto.response.UserResponseDto;
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


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegistrationRequestDto request) {
        UserResponseDto response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

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
