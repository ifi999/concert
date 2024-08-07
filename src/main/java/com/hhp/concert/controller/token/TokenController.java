package com.hhp.concert.controller.token;

import com.hhp.concert.controller.token.dto.GetTokenRequest;
import com.hhp.concert.controller.token.dto.GetTokenResponse;
import com.hhp.concert.controller.token.dto.RenewTokenResponse;
import com.hhp.concert.domain.token.Token;
import com.hhp.concert.domain.token.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/tokens")
    public GetTokenResponse getToken(
            @RequestBody final GetTokenRequest request
    ) {
        final Token token = tokenService.getToken(request.toDomain());

        return GetTokenResponse.from(token);
    }

    @PatchMapping("/tokens/{tokenId}")
    public RenewTokenResponse renewToken(
            @PathVariable final Long tokenId
    ) {
        final Token token = tokenService.renewToken(tokenId);

        return RenewTokenResponse.from(token);
    }

}
