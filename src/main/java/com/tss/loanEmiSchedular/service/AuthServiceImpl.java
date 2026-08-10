package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.dto.JwtResponseDto;
import com.tss.loanEmiSchedular.dto.LoginRequestDto;
import com.tss.loanEmiSchedular.dto.RegistrationRequestDto;
import com.tss.loanEmiSchedular.dto.UserResponseDto;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.enums.Role;
import com.tss.loanEmiSchedular.exception.UserApiException;
import com.tss.loanEmiSchedular.repository.UserRepository;
import com.tss.loanEmiSchedular.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public UserResponseDto register(RegistrationRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserApiException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Public registration can never grant anything but BORROWER — this
        // is not read from the request body, it is hardcoded here.
        user.setRole(Role.BORROWER);

        user = userRepository.save(user);

        return toDto(user);
    }


    @Override
    public JwtResponseDto login(LoginRequestDto loginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            String token = tokenProvider.generateToken(authentication);

            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse(null);

            JwtResponseDto response = new JwtResponseDto();
            response.setAccessToken(token);
            response.setTokenType("Bearer");
            response.setRole(role);

            return response;

        } catch (BadCredentialsException ex) {
            throw new UserApiException("Email or password is incorrect");
        }
    }

    private UserResponseDto toDto(User user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getRole().name());
    }
}
