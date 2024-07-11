package com.hhp.concert.domain.user;

import com.hhp.concert.infra.user.UserPointJpaRepository;
import com.hhp.concert.infra.user.entity.UserPointEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertUserServiceIntegrationTest {

    @Autowired
    private ConcertUserService concertUserService;

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private UserPointService userPointService;

    @Test
    void 동시에_30번의_충전_요청이_오는_경우_30번_모두_충전된다() throws Exception {
        // given
        final ConcertUser 사용자 = concertUserService.enroll(new ConcertUser("사용자1", "222@foo.bar"));

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(30);

        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                try {
                    return userPointService.charge(사용자.getId(), new UserPoint(1_000L));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);

        // when
        final UserPointEntity 충전된_포인트 = userPointJpaRepository.findByUserId(사용자.getId()).get();

        // then
        assertThat(충전된_포인트.getPoint()).isEqualTo(30_000L);
    }

}
