package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.entity.AuditLog;
import com.tss.loanEmiSchedular.entity.Loan;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.enums.ActorType;
import com.tss.loanEmiSchedular.enums.AuditAction;
import com.tss.loanEmiSchedular.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuditLogServiceTest {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Test
    void shouldPersistAuditLog() {
        Loan loan = new Loan();
        loan.setId(1L);

        User user = new User();
        user.setId(2L);
        user.setEmail("auditor@example.com");

        AuditLog auditLog = new AuditLog();
        auditLog.setLoan(loan);
        auditLog.setPerformedBy(user);
        auditLog.setActorType(ActorType.OFFICER);
        auditLog.setAuditAction(AuditAction.LOAN_CREATED);
        auditLog.setRemarks("Loan created successfully");

        AuditLog saved = auditLogRepository.save(auditLog);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getAuditAction()).isEqualTo(AuditAction.LOAN_CREATED);
    }
}
