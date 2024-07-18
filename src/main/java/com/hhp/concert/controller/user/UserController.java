package com.hhp.concert.controller.user;

import com.hhp.concert.controller.user.dto.*;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserService;
import com.hhp.concert.domain.user.UserPoint;
import com.hhp.concert.domain.user.UserPointService;
import org.springframework.web.bind.annotation.*;

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
    public GetBalanceResponse getBalance(
            @PathVariable("userId") final long userId
    ) {
        final UserPoint userPoint = userPointService.getBalance(userId);

        return GetBalanceResponse.from(userPoint);
    }

}
