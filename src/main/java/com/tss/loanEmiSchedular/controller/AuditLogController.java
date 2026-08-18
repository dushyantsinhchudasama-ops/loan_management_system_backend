package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.response.AuditLogResponseDto;
import com.tss.loanEmiSchedular.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<AuditLogResponseDto>> getAuditLogsByLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(auditLogService.findByLoanId(loanId));
    }

    @GetMapping("/user/{performedById}")
    public ResponseEntity<List<AuditLogResponseDto>> getAuditLogsByUser(@PathVariable Long performedById) {
        return ResponseEntity.ok(auditLogService.findByPerformedById(performedById));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDto> getAuditLogById(@PathVariable Long id) {
        return ResponseEntity.ok(auditLogService.findById(id));
    }
}
