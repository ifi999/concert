package com.hhp.concert.controller.concert;

import com.hhp.concert.controller.concert.dto.ReserveSeatRequest;
import com.hhp.concert.infra.concert.*;
import com.hhp.concert.infra.concert.entity.*;
import com.hhp.concert.util.DateTimeProvider;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ConcertControllerTest {

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    @Autowired
    private SeatZoneJpaRepository seatZoneJpaRepository;

    @Autowired
    private SeatTypeJpaRepository seatTypeJpaRepository;

    @Autowired
    private DateTimeProvider dateTimeProvider;

    @Test
    void 콘서트_목록을_조회한다() {
        // given
        final LocalDate 현재시간 = dateTimeProvider.currentDate();

        concertJpaRepository.save(new ConcertEntity(
            "콘서트1",
            "아티스트1",
            "장소1",
            현재시간.plus(7, ChronoUnit.DAYS),
            현재시간.plus(10, ChronoUnit.DAYS)
        ));

        // when
        final JsonPath 콘서트_목록_조회_응답 =
            given()
                .log().all()
            .when()
                .get("/api/concerts")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final String 콘서트명 = 콘서트_목록_조회_응답.getString("[0].concertName");
        final String 아티스트 = 콘서트_목록_조회_응답.getString("[0].artist");
        final String 장소 = 콘서트_목록_조회_응답.getString("[0].venue");

        assertThat(콘서트명).isEqualTo("콘서트1");
        assertThat(아티스트).isEqualTo("아티스트1");
        assertThat(장소).isEqualTo("장소1");
    }

    @Test
    void 콘서트_날짜를_조회한다() {
        // given
        final LocalDate 현재시간 = dateTimeProvider.currentDate();

        final ConcertEntity 콘서트 = concertJpaRepository.save(new ConcertEntity(
            "콘서트1",
            "아티스트1",
            "장소1",
            현재시간.plus(7, ChronoUnit.DAYS),
            현재시간.plus(10, ChronoUnit.DAYS)
        ));

        concertScheduleJpaRepository.save(new ConcertScheduleEntity(
            콘서트,
            현재시간.plus(7, ChronoUnit.DAYS),
            현재시간.plus(7, ChronoUnit.DAYS).atTime(13, 0))
        );

        // when
        final JsonPath 날짜조회_응답 =
            given()
                .log().all()
            .when()
                .get("/api/concerts/{concertId}/dates", 콘서트.getId())
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final String 콘서트_날짜 = 날짜조회_응답.getString("[0].concertDate");

        assertThat(콘서트_날짜).isEqualTo(현재시간.plus(7, ChronoUnit.DAYS).toString());
    }

    @Test
    void 콘서트_좌석을_조회한다() {
        // given
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

        concertSeatJpaRepository.save(new ConcertSeatEntity(콘서트, 스케쥴, 좌석));

        // when
        final JsonPath 좌석조회_응답 =
            given()
                .log().all()
            .when()
                .get("/api/concerts/{concertId}/schedules/{scheduleId}/seats", 콘서트.getId(), 스케쥴.getId())
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final String 조회_좌석명 = 좌석조회_응답.getString("[0].seatName");
        final int 조회_좌석_가격 = 좌석조회_응답.getInt("[0].price");
        final String 조회_좌석_구역 = 좌석조회_응답.getString("[0].zoneName");

        assertThat(조회_좌석명).isEqualTo("일반석");
        assertThat(조회_좌석_가격).isEqualTo(30_000);
        assertThat(조회_좌석_구역).isEqualTo("A열");
    }

    @Test
    void 좌석을_예약한다() {
        // given
        final long 콘서트_ID = 1L;
        final long 콘서트_스케쥴_ID = 2L;
        final long 좌석_ID = 123L;
        final long 사용자_ID = 456L;
        final ReserveSeatRequest 좌석예약_요청 = new ReserveSeatRequest(콘서트_ID, 콘서트_스케쥴_ID, 좌석_ID, 사용자_ID);

        // when
        final JsonPath 좌석예약_응답 =
            given()
                .log().all()
                .body(좌석예약_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/concerts/reservation")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 예약_ID = 좌석예약_응답.getLong("reservationId");
        final String 예약_좌석명 = 좌석예약_응답.getString("seatName");
        final String 예약_시간 = 좌석예약_응답.getString("reservationTime");
        final String 예약_상태 = 좌석예약_응답.getString("reservationStatus");

        assertThat(예약_ID).isEqualTo(1L);
        assertThat(예약_좌석명).isEqualTo("A1");
        assertThat(예약_시간).isEqualTo("2024-07-01T13:00:00");
        assertThat(예약_상태).isEqualTo("RESERVED");
    }

}
