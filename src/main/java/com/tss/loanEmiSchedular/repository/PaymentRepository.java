package com.tss.loanEmiSchedular.repository;


import com.tss.loanEmiSchedular.entity.Payment;
import com.tss.loanEmiSchedular.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByEmiId(Long emiId);


    List<Payment> findByEmiIdAndPaymentStatus(Long emiId, PaymentStatus status);

    List<Payment> findByEmiLoanId(Long loanId);
}
