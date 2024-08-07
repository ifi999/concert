package com.hhp.concert.controller.payment;

import com.hhp.concert.controller.payment.dto.GetUserPaymentResponse;
import com.hhp.concert.controller.payment.dto.PayReservationRequest;
import com.hhp.concert.controller.payment.dto.PayReservationResponse;
import com.hhp.concert.domain.payment.Payment;
import com.hhp.concert.domain.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public PayReservationResponse pay(
        @RequestBody final PayReservationRequest request
    ) {
        final Payment payment = paymentService.pay(request.toDomain());

        return PayReservationResponse.from(payment);
    }

    @GetMapping("/users/{userId}/payments")
    public List<GetUserPaymentResponse> getUserPayments(
        @PathVariable final long userId
    ) {
        final List<Payment> payments = paymentService.getUserPayments(userId);

        return GetUserPaymentResponse.from(payments);
    }

}
