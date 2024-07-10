package com.hhp.concert.controller.user;

import com.hhp.concert.controller.user.dto.*;
import com.hhp.concert.domain.user.TokenStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @PostMapping("/users")
    public EnrollConcertUserResponse enrollUser(
        @RequestBody final EnrollConcertUserRequest request
    ) {
        return new EnrollConcertUserResponse(1L, "유저", "222@2.2");
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
