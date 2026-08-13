package com.tss.loanEmiSchedular.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmiOverdueEvent {
    private Long emiId;
    private String email;

    public EmiOverdueEvent(Long emiId,String email){
        this.emiId=emiId;
        this.email=email;
    }
}
