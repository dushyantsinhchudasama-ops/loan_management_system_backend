package com.tss.loanEmiSchedular.service.Imp;

import com.tss.loanEmiSchedular.dto.request.SystemConfigUpdateRequestDto;
import com.tss.loanEmiSchedular.dto.response.SystemConfigResponseDto;
import com.tss.loanEmiSchedular.entity.SystemConfig;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.exception.ResourceNotFoundException;
import com.tss.loanEmiSchedular.repository.SystemConfigRepository;
import com.tss.loanEmiSchedular.repository.UserRepository;
import com.tss.loanEmiSchedular.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemConfigServiceImpl implements SystemConfigService {

    private final SystemConfigRepository systemConfigRepository;
    private final UserRepository userRepository;

    @Override
    public List<SystemConfigResponseDto> getAllConfigs() {
        return systemConfigRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public Page<SystemConfigResponseDto> getAllConfigsPage(Pageable pageable) {
        return systemConfigRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public SystemConfigResponseDto getConfigByKey(String configKey) {

        return toResponse(findByKeyOrThrow(configKey));
    }

    @Override
    public SystemConfigResponseDto updateConfig(String configKey, SystemConfigUpdateRequestDto request, String adminEmail) {
        SystemConfig config = findByKeyOrThrow(configKey);

        String previousValue = config.getConfigValue();
        config.setConfigValue(request.getConfigValue());

        User admin = userRepository.findByEmail(adminEmail).orElse(null);
        config.setUpdatedBy(admin != null ? admin.getId() : null);

        SystemConfig saved = systemConfigRepository.save(config);

        log.info("SystemConfig '{}' changed from '{}' to '{}' by '{}'",
                configKey, previousValue, request.getConfigValue(), adminEmail);

        return toResponse(saved);
    }

    @Override
    public String getRawValue(String configKey) {
        return findByKeyOrThrow(configKey).getConfigValue();
    }

    @Override
    public BigDecimal getDecimalValue(String configKey) {
        return new BigDecimal(getRawValue(configKey));
    }

    @Override
    public int getIntValue(String configKey) {
        return Integer.parseInt(getRawValue(configKey));
    }

    private SystemConfig findByKeyOrThrow(String configKey) {
        return systemConfigRepository.findByConfigKey(configKey)
                .orElseThrow(() -> new ResourceNotFoundException("SystemConfig with key '" + configKey + "'"));
    }

    private SystemConfigResponseDto toResponse(SystemConfig config) {
        SystemConfigResponseDto dto = new SystemConfigResponseDto();
        dto.setId(config.getId());
        dto.setConfigKey(config.getConfigKey());
        dto.setConfigValue(config.getConfigValue());
        dto.setDescription(config.getDescription());
        dto.setUpdatedBy(config.getUpdatedBy());
        dto.setUpdatedAt(config.getUpdatedAt());
        return dto;
    }
}
