package com.tss.loanEmiSchedular.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Persists a one-time verification code issued for email verification,
 * password reset, or mobile-number change (Section 3.3 / 3.10).
 *
 * Replaces the previous in-memory ConcurrentHashMap approach: OTPs must
 * survive a server restart, carry a real expiry timestamp, and track
 * failed-attempt count so the OTP can be invalidated after too many
 * wrong tries, per Section 3.10.
 *
 * The OTP itself is never stored in plaintext — only a hash of it — so
 * it can never be recovered or leaked via a database dump, API response,
 * or log line.
 */
@Entity
@Table(name = "otps")
@Getter
@Setter
public class Otp extends BaseEntity {

    // Owner of this OTP. A user may not exist yet at registration time in
    // some flows, so the email is also kept directly for lookup.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String email;

    // Never store the raw OTP — only a hashed value (e.g. BCrypt/SHA-256).
    @Column(nullable = false)
    private String otpHash;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private int attemptCount = 0;

    private boolean isVerified = false;

    // Set true once max attempts is exceeded or a fresh OTP is requested
    // for the same purpose, so a stale row can never be reused.
    private boolean isInvalidated = false;
}
