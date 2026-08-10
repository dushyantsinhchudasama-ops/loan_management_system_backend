package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.dto.JwtResponseDto;
import com.tss.loanEmiSchedular.dto.LoginRequestDto;
import com.tss.loanEmiSchedular.dto.RegistrationRequestDto;
import com.tss.loanEmiSchedular.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegistrationRequestDto request);


    JwtResponseDto login(LoginRequestDto loginDto);
}
