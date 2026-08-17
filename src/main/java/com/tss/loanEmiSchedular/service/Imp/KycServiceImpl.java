package com.tss.loanEmiSchedular.service.Imp;

import com.tss.loanEmiSchedular.dto.request.KycRequestDto;
import com.tss.loanEmiSchedular.entity.Address;
import com.tss.loanEmiSchedular.entity.BorrowerProfile;
import com.tss.loanEmiSchedular.entity.FinancialProfile;
import com.tss.loanEmiSchedular.entity.User;
import com.tss.loanEmiSchedular.exception.BusinessException;
import com.tss.loanEmiSchedular.exception.ResourceNotFoundException;
import com.tss.loanEmiSchedular.repository.FinancialProfileRepository;
import com.tss.loanEmiSchedular.repository.UserRepository;
import com.tss.loanEmiSchedular.service.BorrowerRepository;
import com.tss.loanEmiSchedular.service.KycService;
import com.tss.loanEmiSchedular.util.PanHashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KycServiceImpl  implements KycService {
    private  final UserRepository userRepository;
    private  final FinancialProfileRepository  financialProfileRepository;
    private  final BorrowerRepository borrowerRepository;

    @Override
    @Transactional
    public String verifyKyc(KycRequestDto kycRequestDto, String email) {

        String normalizedPan=kycRequestDto.getPan().toUpperCase().trim();

        String hashedPan= PanHashUtil.hashPan(normalizedPan);



        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isEmailVerified()) {
            throw new BusinessException(
                    "Please verify your email address before continuing.",
                    HttpStatus.FORBIDDEN
            );
        }
        if (user.isKycVerified()) {
            throw new BusinessException("KYC already completed", HttpStatus.CONFLICT);        }

        FinancialProfile fp = financialProfileRepository
                .findByPanAndNameAndDob(hashedPan, kycRequestDto.getName(), kycRequestDto.getDob())
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));

        BorrowerProfile borrowerProfile=new BorrowerProfile();
        borrowerProfile.setPan(hashedPan);
        borrowerProfile.setUser(user);
        borrowerProfile.setAadhaar(kycRequestDto.getAadhaar());

        Address address = new Address();
        address.setStreet(kycRequestDto.getAddress().getStreet());
        address.setCity(kycRequestDto.getAddress().getCity());
        address.setState(kycRequestDto.getAddress().getState());
        address.setPincode(kycRequestDto.getAddress().getPincode());

        address.setBorrower(borrowerProfile);
        borrowerProfile.setAddress(address);

        user.setKycVerified(true);

        borrowerRepository.save(borrowerProfile);

        log.info("Kyc successful of user: "+user.getEmail());

        return "KYC successful";

    }

}
