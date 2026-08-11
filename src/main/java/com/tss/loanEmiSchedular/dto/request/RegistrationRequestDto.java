package com.tss.loanEmiSchedular.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Public self-registration payload. There is intentionally NO "role" field
 * here — anyone hitting the public /api/auth/register endpoint always
 * becomes a plain BORROWER (the "User" role). LOAN_OFFICER and ADMIN
 * accounts can only be created by an existing ADMIN via the
 * /api/admin/staff endpoint, so a caller can never grant themselves
 * elevated privileges through registration.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
