package com.hhp.concert.controller.user;

import com.hhp.concert.controller.user.dto.*;
import com.hhp.concert.domain.user.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final ConcertUserService concertUserService;
    private final UserPointService userPointService;

    public UserController(final ConcertUserService concertUserService, final UserPointService userPointService) {
        this.concertUserService = concertUserService;
        this.userPointService = userPointService;
    }

    @PostMapping("/users")
    public EnrollConcertUserResponse enrollUser(
        @RequestBody final EnrollConcertUserRequest request
    ) {
        final ConcertUser enrolledUser = concertUserService.enroll(request.toDomain());

        return EnrollConcertUserResponse.from(enrolledUser);
    }

    @PostMapping("users/{userId}/points/charge")
    public ChargePointResponse charge(
            @PathVariable final Long userId,
            @RequestBody ChargePointRequest request
    ) {
        final UserPoint chargedUserPoint = userPointService.charge(userId, request.toDomain());

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
