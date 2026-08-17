package com.tss.loanEmiSchedular.listener;

import com.tss.loanEmiSchedular.events.EmiOverdueEvent;
import com.tss.loanEmiSchedular.events.EmiPaidEvent;
import com.tss.loanEmiSchedular.events.PaymentReminderEvent;
import com.tss.loanEmiSchedular.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmiEventListener {

    private final EmailService emailService;

    @EventListener
    public void handleEmiOverdue(EmiOverdueEvent event) {
        log.info("Handling overdue event for EMI id: {}, email: {}", event.getEmiId(), event.getEmail());
        emailService.sendEmiOverdueEmail(event.getEmail(), event.getEmiId());
    }

    @EventListener
    public void handlePaymentReminder(PaymentReminderEvent event) {
        log.info("Handling payment reminder for EMI id: {}, email: {}", event.getEmiId(), event.getEmail());
        emailService.sendPaymentReminderEmail(event.getEmail(), event.getEmiId());
    }

    @EventListener
    public void handleEmiPaid(EmiPaidEvent event) {
        Long emiId = event.getEmi().getId();
        String email = event.getBorrower().getEmail();

        if (event.isWasOverdue()) {
            log.info("EMI {} was paid AFTER being overdue. Sending overdue-paid email to {}", emiId, email);
            emailService.sendEmiPaidAfterOverdueEmail(email, emiId);
        } else {
            log.info("EMI {} was paid ON TIME. Sending confirmation email to {}", emiId, email);
            emailService.sendEmiPaidOnTimeEmail(email, emiId);
        }
    }
}