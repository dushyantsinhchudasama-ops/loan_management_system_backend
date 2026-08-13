package com.tss.loanEmiSchedular.service;
import com.tss.loanEmiSchedular.dto.response.BorrowerLoanResponseDto;
import com.tss.loanEmiSchedular.dto.response.EmiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BorrowerService {

    Page<BorrowerLoanResponseDto> getMyLoansByPage(String email, Pageable pageable);

    List<BorrowerLoanResponseDto> getMyLoans(String email);

    List<EmiResponseDto> getEmisForLoan(String email, Long loanId);

}
