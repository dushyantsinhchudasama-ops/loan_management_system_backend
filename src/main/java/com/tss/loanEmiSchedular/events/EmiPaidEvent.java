package com.tss.loanEmiSchedular.events;

import com.tss.loanEmiSchedular.entity.Emi;
import com.tss.loanEmiSchedular.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmiPaidEvent {
    private final Emi emi;
    private final User borrower;
    private final boolean wasOverdue;

    public EmiPaidEvent(Emi emi, User borrower, boolean wasOverdue) {
        this.emi = emi;
        this.borrower = borrower;
        this.wasOverdue = wasOverdue;
    }
}