package com.hhp.concert.controller.token;

import com.hhp.concert.controller.token.dto.GetTokenRequest;
import com.hhp.concert.controller.token.dto.GetTokenResponse;
import com.hhp.concert.controller.token.dto.RenewTokenResponse;
import com.hhp.concert.domain.token.TokenStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TokenController {

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
