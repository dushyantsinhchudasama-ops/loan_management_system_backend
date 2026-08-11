package com.tss.loanEmiSchedular.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SystemConfigResponseDto {
    private Long id;
    private String configKey;
    private String configValue;
    private String description;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
