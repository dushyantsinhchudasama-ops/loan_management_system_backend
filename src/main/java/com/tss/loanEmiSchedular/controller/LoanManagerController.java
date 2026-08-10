package com.tss.loanEmiSchedular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints reachable only by an authenticated LOAN_OFFICER
 * ("loan_manager"), enforced by SecurityConfig's
 * "/api/loan-manager/**" -> hasRole("LOAN_OFFICER") rule.
 */
@RestController
@RequestMapping("/api/loan-manager")
public class LoanManagerController {

    @GetMapping("/loans/pending")
    public ResponseEntity<String> pendingLoans(Authentication authentication) {
        return ResponseEntity.ok(
                "Loans with status = PENDING, awaiting review by " + authentication.getName());
    }

    // Placeholder — would call a LoanService to flip Loan.status from
    // PENDING to APPROVED/REJECTED and write an AuditLog row.
    @PutMapping("/loans/{loanId}/decision")
    public ResponseEntity<String> decide(@PathVariable Long loanId) {
        return ResponseEntity.ok("Approve/reject logic for loan " + loanId + " would run here.");
    }
}
