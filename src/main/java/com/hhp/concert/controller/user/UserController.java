package com.hhp.concert.controller.user;

import com.hhp.concert.controller.user.dto.*;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserService;
import com.hhp.concert.domain.user.TokenStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final ConcertUserService concertUserService;

    public UserController(final ConcertUserService concertUserService) {
        this.concertUserService = concertUserService;
    }

    @PostMapping("/users")
    public EnrollConcertUserResponse enrollUser(
        @RequestBody final EnrollConcertUserRequest request
    ) {
        final ConcertUser enrolledUser = concertUserService.enroll(request.toDomain());

        return EnrollConcertUserResponse.from(enrolledUser);
    }

    @PostMapping("/tokens")
    public GetTokenResponse getToken(
        @RequestBody final GetTokenRequest request
    ) {
        return new GetTokenResponse(123L, 456L, "token", TokenStatus.PENDING, 123);
    }

    @PatchMapping("/tokens/{tokenId}")
    public RenewTokenResponse renewToken(
        @PathVariable final String tokenId
    ) {
        return new RenewTokenResponse(123L, 456L, "token", TokenStatus.PENDING, 123);
    }

}
