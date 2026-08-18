package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.dto.response.AuditLogResponseDto;
import com.tss.loanEmiSchedular.entity.AuditLog;
import com.tss.loanEmiSchedular.entity.Emi;
import com.tss.loanEmiSchedular.entity.Loan;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.enums.ActorType;
import com.tss.loanEmiSchedular.enums.AuditAction;
import com.tss.loanEmiSchedular.enums.LoanStatus;
import com.tss.loanEmiSchedular.enums.LoanStrategyType;

import java.util.List;

public interface AuditLogService {

    AuditLog saveAuditLog(AuditLog auditLog);

    AuditLog logLoanEvent(Loan loan, User performedBy, ActorType actorType,
                          AuditAction action, String remarks);

    AuditLog logLoanStatusChange(Loan loan, User performedBy, ActorType actorType,
                                LoanStatus previousStatus, LoanStatus newStatus,
                                String remarks);

    AuditLog logLoanStrategyChange(Loan loan, User performedBy, ActorType actorType,
                                  LoanStrategyType previousStrategy, LoanStrategyType newStrategy,
                                  String remarks);

    AuditLog logEmiEvent(Loan loan, User performedBy, ActorType actorType,
                         Emi emi, AuditAction action, String remarks);

    AuditLogResponseDto findById(Long id);

    List<AuditLogResponseDto> findByLoanId(Long loanId);

    List<AuditLogResponseDto> findByPerformedById(Long performedById);
}
