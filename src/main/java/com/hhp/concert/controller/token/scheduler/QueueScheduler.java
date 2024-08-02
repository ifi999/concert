package com.hhp.concert.controller.token.scheduler;

import com.hhp.concert.domain.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueueScheduler {

    private final static Logger logger = LoggerFactory.getLogger(QueueScheduler.class);

    private final TokenService tokenService;

    public QueueScheduler(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Scheduled(fixedRate = 60000)
    public void activeTokens() {
        tokenService.activeTokens(3000);
    }

}
