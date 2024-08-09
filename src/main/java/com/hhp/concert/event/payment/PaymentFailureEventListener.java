package com.hhp.concert.event.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PaymentFailureEventListener {

    private final static Logger logger = LoggerFactory.getLogger(PaymentFailureEventListener.class);

    @Async
    @EventListener
    public void handlePaymentFailure(PaymentFailureEvent event) {
        // 실패 이벤트 처리 로직 - 관리자 알림 전송, 추가 복구 작업 등...
        logger.warn("Payment failed for paymentId: {}.", event.getPaymentId());
    }

}
