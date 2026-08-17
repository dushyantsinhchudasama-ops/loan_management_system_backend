package com.tss.loanEmiSchedular.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "system_configs")
@Getter
@Setter
public class SystemConfig extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String configKey;

    @Column(nullable = false)
    private String configValue;

    private String description;
    private Long updatedBy;
}
