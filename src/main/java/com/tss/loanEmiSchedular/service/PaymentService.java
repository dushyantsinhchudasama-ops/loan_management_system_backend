package com.tss.loanEmiSchedular.service;

import com.tss.loanEmiSchedular.dto.response.PaymentHistoryResponseDto;
import com.tss.loanEmiSchedular.dto.response.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    PaymentResponseDto payEmi(String email, Long loanId);

    List<PaymentHistoryResponseDto> getPaymentHistoryByEmi(String email, Long emiId);

    List<PaymentHistoryResponseDto> getPaymentHistory(String email, Long loanId);
}
