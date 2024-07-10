package com.hhp.concert.controller.payment;

import com.hhp.concert.controller.payment.dto.GetUserPaymentResponse;
import com.hhp.concert.controller.payment.dto.PayReservationRequest;
import com.hhp.concert.controller.payment.dto.PayReservationResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @PostMapping("/payments")
    public PayReservationResponse pay(
        @RequestBody final PayReservationRequest request
    ) {
        return new PayReservationResponse(
            123L,
            456L,
            30_000L,
            LocalDateTime.of(2024, 7, 1, 13, 0, 0)
        );
    }

    @GetMapping("/users/{userId}/payments")
    public List<GetUserPaymentResponse> getUserPayments(
        @PathVariable final long userId
    ) {
        return List.of(new GetUserPaymentResponse(
            123L,
            789L,
            456L,
            30_000L,
            LocalDateTime.of(2024, 7, 1, 13, 0, 0))
        );
    }

}
