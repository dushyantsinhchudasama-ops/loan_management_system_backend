package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoints reachable only by an authenticated BORROWER ("User"), enforced
 * by SecurityConfig's "/api/user/**" -> hasRole("BORROWER") rule.
 * A LOAN_OFFICER or ADMIN JWT gets a 403 here even though it's a valid
 * token, because their role doesn't match.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> myProfile(Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "isKycVerified", user.isKycVerified(),
                "isEmailVerified", user.isEmailVerified()
        ));
    }

    // Placeholder for "my loans" — would delegate to a LoanService filtered
    // by the logged-in borrower's BorrowerProfile once that service exists.
    @GetMapping("/loans")
    public ResponseEntity<String> myLoans() {
        return ResponseEntity.ok("List of loans belonging to the logged-in borrower would be returned here.");
    }
}
