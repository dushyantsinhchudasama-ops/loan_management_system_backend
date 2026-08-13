package com.tss.loanEmiSchedular.controller;

import com.tss.loanEmiSchedular.dto.request.KycRequestDto;
import com.tss.loanEmiSchedular.service.KycService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class BorrowerController {

    private final KycService kycService;

    @PostMapping("/kyc")
    public ResponseEntity<String> verifyKyc(@Valid @RequestBody KycRequestDto kycRequestDto, Authentication authentication) {
        System.out.println(authentication);
        return new ResponseEntity<>(kycService.verifyKyc(kycRequestDto,authentication.getName()), HttpStatus.OK);
    }













}
