package com.tss.loanEmiSchedular.mapper;

import com.tss.loanEmiSchedular.dto.response.AuditLogResponseDto;
import com.tss.loanEmiSchedular.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuditLogMapper {

    AuditLogMapper INSTANCE = Mappers.getMapper(AuditLogMapper.class);

    @Mapping(target = "loanId", expression = "java(auditLog.getLoan() != null ? auditLog.getLoan().getId() : null)")
    @Mapping(target = "performedById", expression = "java(auditLog.getPerformedBy() != null ? auditLog.getPerformedBy().getId() : null)")
    @Mapping(target = "performedByEmail", expression = "java(auditLog.getPerformedBy() != null ? auditLog.getPerformedBy().getEmail() : null)")
    @Mapping(target = "actorType", expression = "java(auditLog.getActorType() != null ? auditLog.getActorType().name() : null)")
    @Mapping(target = "auditAction", expression = "java(auditLog.getAuditAction() != null ? auditLog.getAuditAction().name() : null)")
    @Mapping(target = "prevStatus", expression = "java(auditLog.getPrevStatus() != null ? auditLog.getPrevStatus().name() : null)")
    @Mapping(target = "newStatus", expression = "java(auditLog.getNewStatus() != null ? auditLog.getNewStatus().name() : null)")
    @Mapping(target = "emiId", expression = "java(auditLog.getEmi() != null ? auditLog.getEmi().getId() : null)")
    @Mapping(target = "previousStrategy", expression = "java(auditLog.getPreviousStrategy() != null ? auditLog.getPreviousStrategy().name() : null)")
    @Mapping(target = "newStrategy", expression = "java(auditLog.getNewStrategy() != null ? auditLog.getNewStrategy().name() : null)")
    AuditLogResponseDto toResponseDto(AuditLog auditLog);
}
