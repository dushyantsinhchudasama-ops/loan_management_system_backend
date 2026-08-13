package com.tss.loanEmiSchedular.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentReminderEvent {
    private Long emiId;
    private String email;

    public PaymentReminderEvent(Long emiId,String email){
        this.emiId=emiId;
        this.email=email;
    }
}
