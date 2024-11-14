package com.hhp.concert.event.payment;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final ApplicationEventPublisher publisher;

    public PaymentEventPublisher(final ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void success(PaymentSuccessEvent event) {
        publisher.publishEvent(event);
    }

}
