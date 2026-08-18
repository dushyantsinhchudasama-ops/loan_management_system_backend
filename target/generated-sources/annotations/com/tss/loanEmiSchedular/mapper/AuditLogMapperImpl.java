package com.tss.loanEmiSchedular.mapper;

import com.tss.loanEmiSchedular.dto.response.AuditLogResponseDto;
import com.tss.loanEmiSchedular.entity.AuditLog;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-18T11:13:41+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class AuditLogMapperImpl implements AuditLogMapper {

    @Override
    public AuditLogResponseDto toResponseDto(AuditLog auditLog) {
        if ( auditLog == null ) {
            return null;
        }

        AuditLogResponseDto auditLogResponseDto = new AuditLogResponseDto();

        auditLogResponseDto.setId( auditLog.getId() );
        auditLogResponseDto.setRemarks( auditLog.getRemarks() );
        auditLogResponseDto.setCreatedAt( auditLog.getCreatedAt() );
        auditLogResponseDto.setUpdatedAt( auditLog.getUpdatedAt() );

        auditLogResponseDto.setLoanId( auditLog.getLoan() != null ? auditLog.getLoan().getId() : null );
        auditLogResponseDto.setPerformedById( auditLog.getPerformedBy() != null ? auditLog.getPerformedBy().getId() : null );
        auditLogResponseDto.setPerformedByEmail( auditLog.getPerformedBy() != null ? auditLog.getPerformedBy().getEmail() : null );
        auditLogResponseDto.setActorType( auditLog.getActorType() != null ? auditLog.getActorType().name() : null );
        auditLogResponseDto.setAuditAction( auditLog.getAuditAction() != null ? auditLog.getAuditAction().name() : null );
        auditLogResponseDto.setPrevStatus( auditLog.getPrevStatus() != null ? auditLog.getPrevStatus().name() : null );
        auditLogResponseDto.setNewStatus( auditLog.getNewStatus() != null ? auditLog.getNewStatus().name() : null );
        auditLogResponseDto.setEmiId( auditLog.getEmi() != null ? auditLog.getEmi().getId() : null );
        auditLogResponseDto.setPreviousStrategy( auditLog.getPreviousStrategy() != null ? auditLog.getPreviousStrategy().name() : null );
        auditLogResponseDto.setNewStrategy( auditLog.getNewStrategy() != null ? auditLog.getNewStrategy().name() : null );

        return auditLogResponseDto;
    }
}
