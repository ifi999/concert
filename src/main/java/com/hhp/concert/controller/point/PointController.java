package com.hhp.concert.controller.point;

import com.hhp.concert.controller.point.dto.ChargePointRequest;
import com.hhp.concert.controller.point.dto.ChargePointResponse;
import com.hhp.concert.controller.point.dto.GetCurrentBalanceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PointController {

    @PostMapping("/points/charge")
    public ChargePointResponse charge(
        @RequestBody ChargePointRequest request
    ) {
        return new ChargePointResponse(1L, 100_000L);
    }

    @GetMapping("/users/{userId}/points/balance")
    public GetCurrentBalanceResponse GetCurrentBalance(
        @PathVariable("userId") final long userId
    ) {
        return new GetCurrentBalanceResponse(1L, 90_000L);
    }

}
