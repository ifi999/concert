package com.hhp.concert.controller.payment;

import com.hhp.concert.controller.payment.dto.PayReservationRequest;
import com.hhp.concert.controller.payment.dto.PayReservationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping
    public PayReservationResponse pay(
        @RequestBody PayReservationRequest request
    ) {
        return new PayReservationResponse(123L, 456L, 30_000, LocalDateTime.now());
    }

}
