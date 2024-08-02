package com.hhp.concert.domain.concert;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class ConcertServiceCacheIntegrationTest {

    private final static Logger logger = LoggerFactory.getLogger(ConcertServiceCacheIntegrationTest.class);

    @Autowired
    private ConcertService concertService;

    @Test
    void 콘서트_조회_성능_분석() throws Exception {
        // given

        // when
        final long startTime = System.currentTimeMillis();

        int threadPoolCount = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCount);
        int loopCount = 10_000;
        CountDownLatch latch = new CountDownLatch(loopCount);

        for (int i = 0; i < loopCount; i++) {
            executorService.submit(() -> {
                try {
                    concertService.getConcerts();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        final long endTime = System.currentTimeMillis();

        // then
        logger.info("소요시간: {}", (endTime - startTime));
    }

    @Test
    void 콘서트_스케쥴_조회_성능_분석() throws Exception {
        // given

        // when
        final long startTime = System.currentTimeMillis();

        int threadPoolCount = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCount);
        int loopCount = 10_000;
        CountDownLatch latch = new CountDownLatch(loopCount);

        for (int i = 0; i < loopCount; i++) {
            executorService.submit(() -> {
                try {
                    concertService.getConcertSchedules(1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        final long endTime = System.currentTimeMillis();

        // then
        logger.info("소요시간: {}", (endTime - startTime));
    }

}
