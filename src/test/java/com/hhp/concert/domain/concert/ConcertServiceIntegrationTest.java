package com.hhp.concert.domain.concert;

import com.hhp.concert.domain.SeatStatus;
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
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertServiceIntegrationTest {

    @Autowired
    ConcertService concertService;

    @Autowired
    private ConcertUserService concertUserService;

    @Autowired
    private UserPointService userPointService;

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
    private ConcertReservationJpaRepository concertReservationJpaRepository;

    @Autowired
    private DateTimeProvider dateTimeProvider;

    @Test
    void 동시에_예약을_해도_1건만_예약된다() throws Exception {
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

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    return concertService.reserve(new ConcertReservation(사용자.getId(), 콘서트.getId(), 스케쥴.getId(), 콘서트_좌석.getId()));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);

        // when
        final List<ConcertReservationEntity> 예약_목록 = concertReservationJpaRepository.findAll();

        // then
        assertThat(예약_목록.size()).isEqualTo(1);
    }

}
