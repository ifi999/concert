package com.hhp.concert.controller.point;

import com.hhp.concert.controller.point.dto.ChargePointRequest;
import com.hhp.concert.controller.point.dto.ChargePointResponse;
import com.hhp.concert.controller.point.dto.GetCurrentBalanceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
public class PointController {

    @PostMapping("/charge")
    public ChargePointResponse charge(
        @RequestBody ChargePointRequest request
    ) {
        return new ChargePointResponse(1L, 100_000L);
    }

    @GetMapping("/balance")
    public GetCurrentBalanceResponse GetCurrentBalance(
        @RequestParam("userId") final long userId
    ) {
        return new GetCurrentBalanceResponse(1L, 90_000L);
    }

}
