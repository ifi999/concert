package com.hhp.concert.controller.point;

import com.hhp.concert.controller.point.dto.ChargePointRequest;
import com.hhp.concert.controller.point.dto.ChargePointResponse;
import com.hhp.concert.controller.point.dto.GetCurrentBalanceResponse;
import com.hhp.concert.controller.point.dto.GetUserPointHistory;
import com.hhp.concert.domain.point.PointTransactionType;
import com.hhp.concert.domain.point.UserPoint;
import com.hhp.concert.domain.point.UserPointService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PointController {

    private final UserPointService userPointService;

    public PointController(final UserPointService userPointService) {
        this.userPointService = userPointService;
    }

    @PostMapping("/points/charge")
    public ChargePointResponse charge(
        @RequestBody ChargePointRequest request
    ) {
        final UserPoint chargedUserPoint = userPointService.charge(request.toDomain());

        return ChargePointResponse.from(chargedUserPoint);
    }

    @GetMapping("/users/{userId}/points/balance")
    public GetCurrentBalanceResponse GetCurrentBalance(
        @PathVariable("userId") final long userId
    ) {
        return new GetCurrentBalanceResponse(1L, 90_000L);
    }

    @GetMapping("/users/{userId}/points/history")
    public List<GetUserPointHistory> GetUserPointHistories(
            @PathVariable("userId") final long userId
    ) {
        return List.of(new GetUserPointHistory(
            1L,
            123L,
            PointTransactionType.CHARGE,
            100_000L,
            100_000L,
            LocalDateTime.of(2024, 7, 10, 10, 29, 30))
        );
    }

}
