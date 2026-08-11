package com.tss.loanEmiSchedular.repository;

import com.tss.loanEmiSchedular.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findTopByEmailAndIsVerifiedFalseAndIsInvalidatedFalseOrderByCreatedAtDesc(String email);

    List<Otp> findAllByEmailAndIsVerifiedFalseAndIsInvalidatedFalse(String email);
}
