package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.dto.response.JwtResponseDto;
import com.tss.loanEmiSchedular.dto.request.LoginRequestDto;
import com.tss.loanEmiSchedular.dto.request.RegistrationRequestDto;
import com.tss.loanEmiSchedular.dto.response.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegistrationRequestDto request);


    JwtResponseDto login(LoginRequestDto loginDto);
}
