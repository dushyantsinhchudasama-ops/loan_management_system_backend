package com.tss.loanEmiSchedular.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Generic key-value store for every admin-configurable business threshold
 * described in Section 4.7 of the requirements document
 * (D1, D2, T, S, P, R, min/max loan amount, min/max tenure,
 * OTP validity window, OTP max attempts, default interest rate, etc.).
 *
 * This table is standalone — it is never referenced by a foreign key from
 * any other entity. Services read a row by its configKey whenever they need
 * a threshold, instead of relying on a hardcoded Java constant. Admins can
 * update a row's configValue at runtime and the new value takes effect
 * immediately, with no code change or redeploy.
 */
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

    // User who last changed this value (for audit/traceability). Nullable
    // because the row may have been created by a seed/migration script.
    private Long updatedBy;
}
