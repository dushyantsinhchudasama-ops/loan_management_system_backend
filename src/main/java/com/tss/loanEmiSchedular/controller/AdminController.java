package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.request.SystemConfigUpdateRequestDto;
import com.tss.loanEmiSchedular.dto.response.SystemConfigResponseDto;
import com.tss.loanEmiSchedular.dto.response.UserResponseDto;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.exception.UserApiException;
import com.tss.loanEmiSchedular.repository.UserRepository;
import com.tss.loanEmiSchedular.service.AuthService;
import com.tss.loanEmiSchedular.service.Imp.SystemConfigServiceImpl;
import com.tss.loanEmiSchedular.service.SystemConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private  final  SystemConfigService systemConfigService;


    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        List<UserResponseDto> users = userRepository.findAll().stream()
                .map(u -> new UserResponseDto(u.getId(), u.getEmail(), u.getRole().name()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{userId}/deactivate")
    public ResponseEntity<String> deactivate(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserApiException("User not found"));
        user.setActive(false);
        userRepository.save(user);
        return ResponseEntity.ok("User " + userId + " deactivated");
    }

    @GetMapping
    public ResponseEntity<List<SystemConfigResponseDto>> getAllConfigs() {
        return new ResponseEntity<>(systemConfigService.getAllConfigs(), HttpStatus.OK);
    }


    @GetMapping("/page")
    public ResponseEntity<Page<SystemConfigResponseDto>> getAllConfigsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(systemConfigService.getAllConfigsPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/{configKey}")
    public ResponseEntity<SystemConfigResponseDto> getConfig(@PathVariable String configKey) {
        return new ResponseEntity<>(systemConfigService.getConfigByKey(configKey), HttpStatus.OK);
    }


    @PutMapping("/{configKey}")
    public ResponseEntity<SystemConfigResponseDto> updateConfig(
            @PathVariable String configKey,
            @Valid @RequestBody SystemConfigUpdateRequestDto request,
            Authentication authentication) {
        SystemConfigResponseDto updated = systemConfigService.updateConfig(configKey, request, authentication.getName());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
