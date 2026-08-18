package com.tss.loanEmiSchedular.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogResponseDto {
    private Long id;
    private Long loanId;
    private Long performedById;
    private String performedByEmail;
    private String actorType;
    private String auditAction;
    private String prevStatus;
    private String newStatus;
    private Long emiId;
    private String remarks;
    private String previousStrategy;
    private String newStrategy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
