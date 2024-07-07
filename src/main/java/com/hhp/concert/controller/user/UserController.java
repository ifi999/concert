package com.hhp.concert.controller.user;

import com.hhp.concert.controller.user.dto.GetTokenRequest;
import com.hhp.concert.controller.user.dto.GetTokenResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/token")
    public GetTokenResponse getToken(
        @RequestBody final GetTokenRequest request
    ) {
        return new GetTokenResponse(request.getUserId(), "token");
    }

}
