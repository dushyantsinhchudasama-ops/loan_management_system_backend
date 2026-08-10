package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.UserResponseDto;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.exception.UserApiException;
import com.tss.loanEmiSchedular.repository.UserRepository;
import com.tss.loanEmiSchedular.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints reachable only by an authenticated ADMIN, enforced by
 * SecurityConfig's "/api/admin/**" -> hasRole("ADMIN") rule.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;
    private final UserRepository userRepository;

    // The only way a LOAN_OFFICER or a second ADMIN account gets created —
    // must be called by an already-authenticated ADMIN.
//    @PostMapping("/staff")
//    public ResponseEntity<UserResponseDto> createStaff(@Valid @RequestBody StaffRegistrationRequestDto request) {
//        UserResponseDto response = authService.registerStaff(request);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

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
}
