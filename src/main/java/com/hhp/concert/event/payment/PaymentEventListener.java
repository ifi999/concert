package com.hhp.concert.event.payment;

import com.hhp.concert.event.payment.dto.PaymentExternalApiPayload;
import com.hhp.concert.external.api.PaymentApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PaymentEventListener {

    private final static Logger logger = LoggerFactory.getLogger(PaymentEventListener.class);

    private final PaymentApiClient paymentApiClient;

    public PaymentEventListener(final PaymentApiClient paymentApiClient) {
        this.paymentApiClient = paymentApiClient;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePaymentSuccess(PaymentSuccessEvent event) throws Exception {
        try {
            // 결제 성공 후 외부 API 호출
            final String response = paymentApiClient.sendPaymentEvent(new PaymentExternalApiPayload(event));
            
            // 다음 이벤트

        } catch (Exception e) {
            logger.error("Error occurred while processing payment success for paymentId: {}. Error Message: {}", event.getPaymentId(), e.getMessage());

            // 보상 트랜잭션 - 포인트 복구, 예약 상태 복구 등...
        }

    }

}
