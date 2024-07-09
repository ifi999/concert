package com.hhp.concert.controller.concert;

import com.hhp.concert.controller.concert.dto.ReserveSeatRequest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ConcertControllerTest {

    @Test
    void 콘서트_날짜를_조회한다() {
        // given
        final long 콘서트_ID = 1L;

        // when
        final JsonPath 날짜조회_응답 =
            given()
                .log().all()
            .when()
                .get("/api/concert/{concertId}/available-dates", 콘서트_ID)
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final String 콘서트_날짜 = 날짜조회_응답.getString("[0].concertDate");

        assertThat(콘서트_날짜).isEqualTo("2024-07-01T12:00:00");
    }

    @Test
    void 콘서트_좌석을_조회한다() {
        // given
        final long 콘서트_ID = 1L;

        // when
        final JsonPath 좌석조회_응답 =
            given()
                .log().all()
            .when()
                .get("/api/concert/{concertId}/available-seats", 콘서트_ID)
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 조회_좌석_ID = 좌석조회_응답.getLong("[0].seatId");
        final String 조회_좌석명 = 좌석조회_응답.getString("[0].seatName");
        final int 조회_좌석_가격 = 좌석조회_응답.getInt("[0].price");
        final String 조회_좌석_구역 = 좌석조회_응답.getString("[0].zone");

        assertThat(조회_좌석_ID).isEqualTo(1L);
        assertThat(조회_좌석명).isEqualTo("A1");
        assertThat(조회_좌석_가격).isEqualTo(30_000);
        assertThat(조회_좌석_구역).isEqualTo("Standard");
    }

    @Test
    void 좌석을_예약한다() {
        // given
        final long 콘서트_ID = 1L;
        final long 좌석_ID = 123L;
        final long 사용자_ID = 456L;
        final ReserveSeatRequest 좌석예약_요청 = new ReserveSeatRequest(좌석_ID, 사용자_ID);

        // when
        final JsonPath 좌석예약_응답 =
            given()
                .log().all()
                .body(좌석예약_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/concert/{concertId}/reserve-seat", 콘서트_ID)
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
