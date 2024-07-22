package com.hhp.concert.support.interceptor;

import com.hhp.concert.domain.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    private final TokenService tokenService;

    public TokenInterceptor(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String token = request.getHeader("Authorization");

        if (token == null || !tokenService.auth(token)) {
            logger.warn("Unauthorized request: Invalid token. Token: {} Request URL: {} Method: {}", token, request.getRequestURL(), request.getMethod());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return false;
        }

        tokenService.updateTokenActivityTime(token);

        return true;
    }


}
