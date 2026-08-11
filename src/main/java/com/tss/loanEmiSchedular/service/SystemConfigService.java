package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.dto.request.SystemConfigUpdateRequestDto;
import com.tss.loanEmiSchedular.dto.response.SystemConfigResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface SystemConfigService {
    List<SystemConfigResponseDto> getAllConfigs();

    Page<SystemConfigResponseDto> getAllConfigsPage(Pageable pageable);

    SystemConfigResponseDto getConfigByKey(String configKey);

    SystemConfigResponseDto updateConfig(String configKey, SystemConfigUpdateRequestDto request, String adminEmail);

    String getRawValue(String configKey);

    BigDecimal getDecimalValue(String configKey);

    int getIntValue(String configKey);
}
