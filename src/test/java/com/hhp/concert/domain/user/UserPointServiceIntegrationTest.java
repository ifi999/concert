package com.hhp.concert.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserPointServiceIntegrationTest {

    @Autowired
    private ConcertUserService concertUserService;

    @Autowired
    private UserPointService userPointService;

    @Test
    void 포인트를_충전_후_잔액_조회하면_충전_금액을_확인할_수_잇다() {
        // given
        final ConcertUser 사용자 = concertUserService.enroll(new ConcertUser("사용자1", "222@foo.bar"));
        userPointService.charge(사용자.getId(), new UserPoint(100_000L));

        // when
        final UserPoint 조회_잔액 = userPointService.getBalance(사용자.getId());

        // then
        Assertions.assertThat(조회_잔액.getUserId()).isEqualTo(사용자.getId());
        Assertions.assertThat(조회_잔액.getPoint()).isEqualTo(100_000L);
    }

    @Test
    void 포인트를_여러_번_충전하면_포인트가_누적되어_잔액_조회가_된다() {
        // given
        final ConcertUser 사용자 = concertUserService.enroll(new ConcertUser("사용자1", "222@foo.bar"));
        userPointService.charge(사용자.getId(), new UserPoint(100_000L));
        userPointService.charge(사용자.getId(), new UserPoint(10_000L));
        userPointService.charge(사용자.getId(), new UserPoint(1_000L));

        // when
        final UserPoint 조회_잔액 = userPointService.getBalance(사용자.getId());

        // then
        Assertions.assertThat(조회_잔액.getUserId()).isEqualTo(사용자.getId());
        Assertions.assertThat(조회_잔액.getPoint()).isEqualTo(111_000L);
    }

}
