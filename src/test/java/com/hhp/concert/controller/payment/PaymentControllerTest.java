package com.hhp.concert.controller.payment;

import com.hhp.concert.controller.payment.dto.PayReservationRequest;
import com.hhp.concert.domain.SeatStatus;
import com.hhp.concert.domain.concert.ConcertReservationStatus;
import com.hhp.concert.infra.concert.*;
import com.hhp.concert.infra.concert.entity.*;
import com.hhp.concert.infra.payment.PaymentJpaRepository;
import com.hhp.concert.infra.payment.entity.PaymentEntity;
import com.hhp.concert.infra.token.TokenJpaRepository;
import com.hhp.concert.infra.token.entity.TokenEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.UserPointJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.infra.user.entity.UserPointEntity;
import com.hhp.concert.support.util.DateTimeProvider;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PaymentControllerTest {

    @Autowired
    private ConcertUserJpaRepository concertUserJpaRepository;

    @Autowired
    private DateTimeProvider dateTimeProvider;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private SeatZoneJpaRepository seatZoneJpaRepository;

    @Autowired
    private SeatTypeJpaRepository seatTypeJpaRepository;

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private ConcertReservationJpaRepository concertReservationJpaRepository;

    @Autowired
    private PaymentJpaRepository paymentJpaRepository;

    @Autowired
    private TokenJpaRepository tokenJpaRepository;

    private ConcertUserEntity 사용자;
    private ConcertReservationEntity 예약;

    private String authToken;

    @BeforeEach
    void setUp() {
        //
        final LocalDate 현재시간 = dateTimeProvider.currentDate();

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

        사용자 = concertUserJpaRepository.save(new ConcertUserEntity("사용자1", "222@foo.bar"));

        final UserPointEntity 포인트 = userPointJpaRepository.save(new UserPointEntity(사용자, 100_000L));

        예약 = concertReservationJpaRepository.save(
            new ConcertReservationEntity(
                사용자,
                스케쥴,
                콘서트_좌석,
                30_000L,
                ConcertReservationStatus.PENDING
            )
        );

        authToken = UUID.randomUUID().toString();
        final TokenEntity 토큰 = new TokenEntity(사용자, authToken, LocalDateTime.now());
        tokenJpaRepository.save(토큰);
    }

    @Test
    void 결제한다() {
        // given
        final PayReservationRequest 결제_요청 = new PayReservationRequest(예약.getId(), 사용자.getId(), 30_000L);

        // when
        final JsonPath 결제예약_응답 =
            given()
                .log().all()
                .header("Authorization", authToken)
                .body(결제_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/payments")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 응답_예약_ID = 결제예약_응답.getLong("reservationId");
        final long 응답_사용자_ID = 결제예약_응답.getLong("userId");
        final int 응답_결제_금액 = 결제예약_응답.getInt("paymentAmount");

        assertThat(응답_결제_금액).isEqualTo(30_000L);
        assertThat(응답_예약_ID).isEqualTo(예약.getId());
        assertThat(응답_사용자_ID).isEqualTo(사용자.getId());
    }

    @Test
    void 결제_목록을_조회한다() {
        // given
        paymentJpaRepository.save(new PaymentEntity(사용자, 예약, 30_000L));

        // when
        final JsonPath 결제목록_응답 =
            given()
                .log().all()
                .header("Authorization", authToken)
            .when()
                .get("/api/users/{userId}/payments", 사용자.getId())
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 결제_예약_ID = 결제목록_응답.getLong("[0].reservationId");
        final long 결제_사용자_ID = 결제목록_응답.getLong("[0].userId");
        final long 결제_금액 = 결제목록_응답.getLong("[0].paymentPrice");

        assertThat(결제_예약_ID).isEqualTo(예약.getId());
        assertThat(결제_사용자_ID).isEqualTo(사용자.getId());
        assertThat(결제_금액).isEqualTo(30_000L);
    }

}
