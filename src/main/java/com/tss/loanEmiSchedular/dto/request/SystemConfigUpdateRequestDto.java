package com.tss.loanEmiSchedular.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemConfigUpdateRequestDto {

    @NotBlank(message = "configValue is required")
    private String configValue;
}
