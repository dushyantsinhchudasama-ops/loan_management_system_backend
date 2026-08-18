package com.tss.loanEmiSchedular.mapper;

import com.tss.loanEmiSchedular.dto.response.BorrowerLoanResponseDto;
import com.tss.loanEmiSchedular.dto.response.EmiResponseDto;
import com.tss.loanEmiSchedular.dto.response.PaymentHistoryResponseDto;
import com.tss.loanEmiSchedular.dto.response.PaymentResponseDto;
import com.tss.loanEmiSchedular.entity.Emi;
import com.tss.loanEmiSchedular.entity.Loan;
import com.tss.loanEmiSchedular.entity.Payment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-18T11:13:41+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class BorrowerMapperImpl implements BorrowerMapper {

    @Override
    public BorrowerLoanResponseDto toLoanResponseDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        BorrowerLoanResponseDto borrowerLoanResponseDto = new BorrowerLoanResponseDto();

        borrowerLoanResponseDto.setLoanId( loan.getId() );
        if ( loan.getLoanType() != null ) {
            borrowerLoanResponseDto.setLoanType( loan.getLoanType().name() );
        }
        if ( loan.getStatus() != null ) {
            borrowerLoanResponseDto.setStatus( loan.getStatus().name() );
        }
        borrowerLoanResponseDto.setLoanAmount( loan.getLoanAmount() );
        borrowerLoanResponseDto.setTenure( loan.getTenure() );
        borrowerLoanResponseDto.setInterestRate( loan.getInterestRate() );
        borrowerLoanResponseDto.setCreditScore( loan.getCreditScore() );

        return borrowerLoanResponseDto;
    }

    @Override
    public List<BorrowerLoanResponseDto> toLoanDtoList(List<Loan> loans) {
        if ( loans == null ) {
            return null;
        }

        List<BorrowerLoanResponseDto> list = new ArrayList<BorrowerLoanResponseDto>( loans.size() );
        for ( Loan loan : loans ) {
            list.add( toLoanResponseDto( loan ) );
        }

        return list;
    }

    @Override
    public EmiResponseDto toEmiDto(Emi emi) {
        if ( emi == null ) {
            return null;
        }

        EmiResponseDto emiResponseDto = new EmiResponseDto();

        emiResponseDto.setEmiId( emi.getId() );
        if ( emi.getEmiStatus() != null ) {
            emiResponseDto.setEmiStatus( emi.getEmiStatus().name() );
        }
        emiResponseDto.setInstallmentNumber( emi.getInstallmentNumber() );
        emiResponseDto.setPrincipal( emi.getPrincipal() );
        emiResponseDto.setInterest( emi.getInterest() );
        emiResponseDto.setTotalDueAmount( emi.getTotalDueAmount() );
        emiResponseDto.setTotalPaidAmount( emi.getTotalPaidAmount() );
        emiResponseDto.setPenaltyAmount( emi.getPenaltyAmount() );
        emiResponseDto.setFullyPaid( emi.isFullyPaid() );
        emiResponseDto.setDueDate( emi.getDueDate() );

        return emiResponseDto;
    }

    @Override
    public List<EmiResponseDto> toEmiResponseDtoList(List<Emi> emis) {
        if ( emis == null ) {
            return null;
        }

        List<EmiResponseDto> list = new ArrayList<EmiResponseDto>( emis.size() );
        for ( Emi emi : emis ) {
            list.add( toEmiDto( emi ) );
        }

        return list;
    }

    @Override
    public PaymentResponseDto toPaymentDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();

        paymentResponseDto.setEmiId( paymentEmiId( payment ) );
        paymentResponseDto.setInstallmentNumber( paymentEmiInstallmentNumber( payment ) );
        paymentResponseDto.setAmountPaid( payment.getAmount() );
        if ( payment.getPaymentStatus() != null ) {
            paymentResponseDto.setPaymentStatus( payment.getPaymentStatus().name() );
        }
        Long id1 = paymentEmiLoanId( payment );
        if ( id1 != null ) {
            paymentResponseDto.setLoanId( String.valueOf( id1 ) );
        }
        paymentResponseDto.setTransactionId( payment.getTransactionId() );

        return paymentResponseDto;
    }

    @Override
    public PaymentHistoryResponseDto toPaymentHistoryDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentHistoryResponseDto paymentHistoryResponseDto = new PaymentHistoryResponseDto();

        paymentHistoryResponseDto.setPaymentId( payment.getId() );
        paymentHistoryResponseDto.setEmiId( paymentEmiId( payment ) );
        paymentHistoryResponseDto.setInstallmentNumber( paymentEmiInstallmentNumber( payment ) );
        if ( payment.getPaymentStatus() != null ) {
            paymentHistoryResponseDto.setPaymentStatus( payment.getPaymentStatus().name() );
        }
        paymentHistoryResponseDto.setPaidAt( payment.getCreatedAt() );
        paymentHistoryResponseDto.setLoanId( paymentEmiLoanId( payment ) );
        paymentHistoryResponseDto.setAmount( payment.getAmount() );
        paymentHistoryResponseDto.setTransactionId( payment.getTransactionId() );

        return paymentHistoryResponseDto;
    }

    @Override
    public List<PaymentHistoryResponseDto> toPaymentHistoryDtoList(List<Payment> payments) {
        if ( payments == null ) {
            return null;
        }

        List<PaymentHistoryResponseDto> list = new ArrayList<PaymentHistoryResponseDto>( payments.size() );
        for ( Payment payment : payments ) {
            list.add( toPaymentHistoryDto( payment ) );
        }

        return list;
    }

    private Long paymentEmiId(Payment payment) {
        Emi emi = payment.getEmi();
        if ( emi == null ) {
            return null;
        }
        return emi.getId();
    }

    private Integer paymentEmiInstallmentNumber(Payment payment) {
        Emi emi = payment.getEmi();
        if ( emi == null ) {
            return null;
        }
        return emi.getInstallmentNumber();
    }

    private Long paymentEmiLoanId(Payment payment) {
        Emi emi = payment.getEmi();
        if ( emi == null ) {
            return null;
        }
        Loan loan = emi.getLoan();
        if ( loan == null ) {
            return null;
        }
        return loan.getId();
    }
}
