package com.tss.loanEmiSchedular.repository;

import com.tss.loanEmiSchedular.entity.AuditLog;
import com.tss.loanEmiSchedular.enums.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByLoanIdOrderByCreatedAtDesc(Long loanId);

    List<AuditLog> findByPerformedByIdOrderByCreatedAtDesc(Long performedById);

    List<AuditLog> findByLoanIdAndAuditActionOrderByCreatedAtDesc(Long loanId, AuditAction action);
}
