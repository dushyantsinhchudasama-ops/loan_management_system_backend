package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.response.JwtResponseDto;
import com.tss.loanEmiSchedular.dto.request.LoginRequestDto;
import com.tss.loanEmiSchedular.dto.request.RegistrationRequestDto;
import com.tss.loanEmiSchedular.dto.response.UserResponseDto;
import com.tss.loanEmiSchedular.service.AuthService;
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
}
