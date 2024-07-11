package com.hhp.concert.controller.point;

import com.hhp.concert.controller.point.dto.ChargePointRequest;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PointControllerTest {

    @Autowired
    private ConcertUserJpaRepository concertUserJpaRepository;

    @Test
    void 포인트를_충전한다() {
        // given
        final ConcertUserEntity 사용자_엔티티 = concertUserJpaRepository.save(new ConcertUserEntity("사용자1", "222@foo.bar"));
        final long 사용자_ID = 사용자_엔티티.getId();

        final int 충전_금액 = 100_000;
        final ChargePointRequest 충전_요청 = new ChargePointRequest(사용자_ID, 충전_금액);

        // when
        final JsonPath 포인트충전_응답 =
            given()
                .log().all()
                .body(충전_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/points/charge")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 응답_사용자_ID = 포인트충전_응답.getLong("userId");
        final long 응답_사용자_보유_포인트 = 포인트충전_응답.getLong("currentBalance");

        assertThat(응답_사용자_ID).isPositive();
        assertThat(응답_사용자_보유_포인트).isEqualTo(100_000L);
    }

    @Test
    void 포인트를_조회한다() {
        // given
        final long 사용자_ID = 1L;

        // when
        final JsonPath 포인트조회_응답 =
            given()
                .log().all()
            .when()
                .get("/api/users/{userId}/points/balance", 사용자_ID)
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 응답_사용자_ID = 포인트조회_응답.getLong("userId");
        final long 응답_사용자_보유_포인트 = 포인트조회_응답.getLong("currentBalance");

        assertThat(응답_사용자_ID).isEqualTo(1L);
        assertThat(응답_사용자_보유_포인트).isEqualTo(90_000L);
    }

}
