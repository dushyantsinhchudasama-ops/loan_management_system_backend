package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.request.KycRequestDto;
import com.tss.loanEmiSchedular.dto.response.BorrowerLoanResponseDto;
import com.tss.loanEmiSchedular.dto.response.EmiResponseDto;
import com.tss.loanEmiSchedular.dto.response.PaymentHistoryResponseDto;
import com.tss.loanEmiSchedular.dto.response.PaymentResponseDto;
import com.tss.loanEmiSchedular.service.BorrowerService;
import com.tss.loanEmiSchedular.service.KycService;
import com.tss.loanEmiSchedular.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class BorrowerController {

    private final KycService kycService;
    private final BorrowerService borrowerService;
    private  final PaymentService paymentService;

    @PostMapping("/kyc")
    public ResponseEntity<String> verifyKyc(@Valid @RequestBody KycRequestDto kycRequestDto, Authentication authentication) {
        System.out.println(authentication);
        return new ResponseEntity<>(kycService.verifyKyc(kycRequestDto,authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/loans/pages")
    public ResponseEntity<Page<BorrowerLoanResponseDto>> getAllLoans(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                                     @RequestParam(defaultValue = "asc") String direction, Authentication authentication) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.status(HttpStatus.OK).body(borrowerService.getMyLoansByPage(authentication.getName(),pageable));
    }

    @GetMapping("/loans/{loanId}/emis")
    public ResponseEntity<List<EmiResponseDto>> getEmis(@PathVariable Long loanId, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(borrowerService.getEmisForLoan(authentication.getName(), loanId));
    }


    @PostMapping("/loans/{loanId}/emis/pay")
    public ResponseEntity<PaymentResponseDto> payEmi(@PathVariable Long loanId, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.payEmi(authentication.getName(), loanId));
    }

    @GetMapping("/loans/emi/{emiId}/history")
    public ResponseEntity<List<PaymentHistoryResponseDto>> getHistoryByEmi(@PathVariable Long emiId, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentHistoryByEmi(authentication.getName(), emiId));
    }

    @GetMapping("/loans/{loanId}/history")
    public ResponseEntity<List<PaymentHistoryResponseDto>> getHistory(@PathVariable Long loanId, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentHistory(authentication.getName(), loanId));
    }
















}
