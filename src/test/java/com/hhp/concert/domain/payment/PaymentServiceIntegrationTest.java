package com.hhp.concert.domain.payment;

import com.hhp.concert.domain.SeatStatus;
import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.concert.ConcertService;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserService;
import com.hhp.concert.domain.user.UserPoint;
import com.hhp.concert.domain.user.UserPointService;
import com.hhp.concert.infra.concert.*;
import com.hhp.concert.infra.concert.entity.*;
import com.hhp.concert.support.util.DateTimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ConcertUserService concertUserService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    @Autowired
    private SeatZoneJpaRepository seatZoneJpaRepository;

    @Autowired
    private SeatTypeJpaRepository seatTypeJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;


    @Autowired
    private DateTimeProvider dateTimeProvider;

    @Test
    void 동시에_결제를_해도_1건만_결제된다() throws Exception {
        // given
        final LocalDate 현재시간 = dateTimeProvider.currentDate();

        final ConcertUser 사용자 = concertUserService.enroll(new ConcertUser("사용자1", "222@foo.bar"));
        userPointService.charge(사용자.getId(), new UserPoint(100_000L));

        final ConcertEntity 콘서트 = concertJpaRepository.save(new ConcertEntity(
            "콘서트1",
            "아티스트1",
            "장소1",
            현재시간.plus(7, ChronoUnit.DAYS),
            현재시간.plus(10, ChronoUnit.DAYS)
        ));

        final ConcertScheduleEntity 스케쥴 = concertScheduleJpaRepository.save(new ConcertScheduleEntity(
            콘서트,
            현재시간.plus(7, ChronoUnit.DAYS),
            현재시간.plus(7, ChronoUnit.DAYS).atTime(13, 0))
        );

        final SeatZoneEntity 좌석_구역 = seatZoneJpaRepository.save(new SeatZoneEntity("A열", 1));
        final SeatTypeEntity 좌석_타입 = seatTypeJpaRepository.save(new SeatTypeEntity("일반석", 30_000L));
        final SeatEntity 좌석 = seatJpaRepository.save(new SeatEntity(좌석_구역, 좌석_타입, "A1"));

        final ConcertSeatEntity 콘서트_좌석 = concertSeatJpaRepository.save(new ConcertSeatEntity(콘서트, 스케쥴, 좌석, SeatStatus.AVAILABLE));

        final ConcertReservation 예약 = concertService.reserve(new ConcertReservation(사용자.getId(), 콘서트.getId(), 스케쥴.getId(), 콘서트_좌석.getId()));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10_000);

        /**
         * 비관적 락
         * 1. thread 50 / count 10_000
         * 1) 8629
         * 2) 8219
         * 3) 8315
         * 4) 8582
         * 5) 8423
         *
         * 2. thread 100 / count 10_000
         * 1) 7823
         * 2) 8726
         * 3) 8689
         * 4) 8275
         * 5) 7752
         *
         * 3. thread 100 / count 100_000
         * 1) 실패. 리소스 부족인듯. 도중에 테스트가 멈추고 실패함 (Unexpected type tag 0 found.)
         *
         * 4.thread 10 / count 10_000
         * 1) 8325
         * 2) 8081
         * 3) 8103
         * 4) 9426
         * 5) 7847
         */
        for (int i = 0; i < 10_000; i++) {
            executorService.submit(() -> {
                try {
                    return paymentService.pay(new Payment(예약.getReservationId(), 사용자.getId(), 30_000L));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);

        // when
        final UserPoint 결제_후_잔액 = userPointService.getBalance(사용자.getId());

        // then
        assertThat(결제_후_잔액.getPoint()).isEqualTo(70_000L);
    }

}
