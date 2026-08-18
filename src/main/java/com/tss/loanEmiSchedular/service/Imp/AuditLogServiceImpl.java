package com.tss.loanEmiSchedular.service.Imp;

import com.tss.loanEmiSchedular.dto.response.AuditLogResponseDto;
import com.tss.loanEmiSchedular.entity.AuditLog;
import com.tss.loanEmiSchedular.entity.Emi;
import com.tss.loanEmiSchedular.entity.Loan;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.enums.ActorType;
import com.tss.loanEmiSchedular.enums.AuditAction;
import com.tss.loanEmiSchedular.enums.LoanStatus;
import com.tss.loanEmiSchedular.enums.LoanStrategyType;
import com.tss.loanEmiSchedular.mapper.AuditLogMapper;
import com.tss.loanEmiSchedular.repository.AuditLogRepository;
import com.tss.loanEmiSchedular.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper = AuditLogMapper.INSTANCE;

    @Override
    public AuditLog saveAuditLog(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    @Override
    public AuditLog logLoanEvent(Loan loan, User performedBy, ActorType actorType,
                                 AuditAction action, String remarks) {
        AuditLog log = new AuditLog();
        log.setLoan(loan);
        log.setPerformedBy(performedBy);
        log.setActorType(actorType);
        log.setAuditAction(action);
        log.setRemarks(remarks);
        return saveAuditLog(log);
    }

    @Override
    public AuditLog logLoanStatusChange(Loan loan, User performedBy, ActorType actorType,
                                       LoanStatus previousStatus, LoanStatus newStatus,
                                       String remarks) {
        AuditLog log = new AuditLog();
        log.setLoan(loan);
        log.setPerformedBy(performedBy);
        log.setActorType(actorType);
        log.setAuditAction(AuditAction.LOAN_STATUS_CHANGED);
        log.setPrevStatus(previousStatus);
        log.setNewStatus(newStatus);
        log.setRemarks(remarks);
        return saveAuditLog(log);
    }

    @Override
    public AuditLog logLoanStrategyChange(Loan loan, User performedBy, ActorType actorType,
                                         LoanStrategyType previousStrategy, LoanStrategyType newStrategy,
                                         String remarks) {
        AuditLog log = new AuditLog();
        log.setLoan(loan);
        log.setPerformedBy(performedBy);
        log.setActorType(actorType);
        log.setAuditAction(AuditAction.LOAN_STRATEGY_CHANGED);
        log.setPreviousStrategy(previousStrategy);
        log.setNewStrategy(newStrategy);
        log.setRemarks(remarks);
        return saveAuditLog(log);
    }

    @Override
    public AuditLog logEmiEvent(Loan loan, User performedBy, ActorType actorType,
                                Emi emi, AuditAction action, String remarks) {
        AuditLog log = new AuditLog();
        log.setLoan(loan);
        log.setPerformedBy(performedBy);
        log.setActorType(actorType);
        log.setAuditAction(action);
        log.setEmi(emi);
        log.setRemarks(remarks);
        return saveAuditLog(log);
    }

    @Override
    public AuditLogResponseDto findById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Audit log not found with id: " + id));
        return auditLogMapper.toResponseDto(auditLog);
    }

    @Override
    public List<AuditLogResponseDto> findByLoanId(Long loanId) {
        return auditLogRepository.findByLoanIdOrderByCreatedAtDesc(loanId)
                .stream()
                .map(auditLogMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<AuditLogResponseDto> findByPerformedById(Long performedById) {
        return auditLogRepository.findByPerformedByIdOrderByCreatedAtDesc(performedById)
                .stream()
                .map(auditLogMapper::toResponseDto)
                .toList();
    }
}
