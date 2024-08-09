package com.hhp.concert.external.api;

import com.hhp.concert.event.payment.dto.PaymentExternalApiPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentApiClient {

    private final static Logger logger = LoggerFactory.getLogger(PaymentApiClient.class);

    public String sendPaymentEvent(final PaymentExternalApiPayload payload) {
        return "Concert Payment: " + payload;
    }

}
