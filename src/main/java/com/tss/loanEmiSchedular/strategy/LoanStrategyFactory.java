package com.tss.loanEmiSchedular.strategy;

import com.tss.loanEmiSchedular.strategy.imp.HighRiskStrategy;
import com.tss.loanEmiSchedular.strategy.imp.LowRiskStrategy;
import com.tss.loanEmiSchedular.strategy.imp.MidRiskStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoanStrategyFactory {

    private final LowRiskStrategy lowRiskStrategy;
    private final MidRiskStrategy midRiskStrategy;
    private final HighRiskStrategy highRiskStrategy;

    public LoanStrategy getStrategy(int dti) {

        if (dti  < 20) {
            return lowRiskStrategy;
        } else if (dti >=20 && dti <= 40  ) { 
            return midRiskStrategy;
        } else {
            return highRiskStrategy;
        }
    }
}