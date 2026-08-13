package com.tss.loanEmiSchedular.mapper;

import com.tss.loanEmiSchedular.dto.response.LoanSummaryResponseDto;
import com.tss.loanEmiSchedular.entity.BorrowerProfile;
import com.tss.loanEmiSchedular.entity.Loan;
import com.tss.loanEmiSchedular.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-13T12:08:00+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanSummaryResponseDto toSummaryResponseDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanSummaryResponseDto loanSummaryResponseDto = new LoanSummaryResponseDto();

        loanSummaryResponseDto.setBorrowerEmail( loanBorrowerUserEmail( loan ) );
        loanSummaryResponseDto.setLoanId( loan.getId() );
        loanSummaryResponseDto.setLoanAmount( loan.getLoanAmount() );
        loanSummaryResponseDto.setTenure( loan.getTenure() );
        loanSummaryResponseDto.setDti( loan.getDti() );
        if ( loan.getSuggestedStrategy() != null ) {
            loanSummaryResponseDto.setSuggestedStrategy( loan.getSuggestedStrategy().name() );
        }
        loanSummaryResponseDto.setCreditScore( loan.getCreditScore() );
        if ( loan.getStatus() != null ) {
            loanSummaryResponseDto.setStatus( loan.getStatus().name() );
        }

        return loanSummaryResponseDto;
    }

    private String loanBorrowerUserEmail(Loan loan) {
        BorrowerProfile borrower = loan.getBorrower();
        if ( borrower == null ) {
            return null;
        }
        User user = borrower.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getEmail();
    }
}
