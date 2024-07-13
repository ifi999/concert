package com.hhp.concert.controller.user;

import com.hhp.concert.controller.user.dto.ChargePointRequest;
import com.hhp.concert.controller.user.dto.EnrollConcertUserRequest;
import com.hhp.concert.controller.token.dto.GetTokenRequest;
import com.hhp.concert.domain.user.UserPoint;
import com.hhp.concert.domain.user.UserPointService;
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
class UserControllerTest {

    @Autowired
    private ConcertUserJpaRepository concertUserJpaRepository;

    @Autowired
    private UserPointService userPointService;

    @Test
    void 토큰을_발급받는다() {
        // given
        final ConcertUserEntity 사용자_엔티티 = concertUserJpaRepository.save(new ConcertUserEntity("사용자1", "222@foo.bar"));
        final long 사용자_ID = 사용자_엔티티.getId();
        final GetTokenRequest 토큰발급_요청 = new GetTokenRequest(사용자_ID);

        // when
        final JsonPath 토큰발급_응답 =
            given()
                .log().all()
                .body(토큰발급_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/tokens")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final long 응답_사용자_ID = 토큰발급_응답.getLong("userId");
        final String 응답_토큰_상태 = 토큰발급_응답.getString("tokenStatus");

        assertThat(응답_사용자_ID).isEqualTo(사용자_ID);
        assertThat(응답_토큰_상태).isEqualTo("PENDING");
    }

    @Test
    void 사용자를_등록한다() {
        // given
        final String 이름 = "사용자1";
        final String 메일 = "222@foo.bar";
        final EnrollConcertUserRequest 사용자등록_요청 = new EnrollConcertUserRequest(이름, 메일);

        // when
        final JsonPath 사용자등록_응답 =
            given()
                .log().all()
                .body(사용자등록_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/users")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final String 응답_사용자_이름 = 사용자등록_응답.getString("name");
        final String 응답_사용자_메일 = 사용자등록_응답.getString("email");

        assertThat(응답_사용자_이름).isEqualTo("사용자1");
        assertThat(응답_사용자_메일).isEqualTo("222@foo.bar");
    }

    @Test
    void 포인트를_충전한다() {
        // given
        final ConcertUserEntity 사용자_엔티티 = concertUserJpaRepository.save(new ConcertUserEntity("사용자1", "222@foo.bar"));
        final long 사용자_ID = 사용자_엔티티.getId();

        final long 충전_금액 = 100_000L;
        final ChargePointRequest 충전_요청 = new ChargePointRequest(충전_금액);

        // when
        final JsonPath 포인트충전_응답 =
            given()
                .log().all()
                .body(충전_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/users/{userId}/points/charge", 사용자_ID)
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
        final ConcertUserEntity 사용자_엔티티 = concertUserJpaRepository.save(new ConcertUserEntity("사용자1", "222@foo.bar"));
        final long 사용자_ID = 사용자_엔티티.getId();

        userPointService.charge(사용자_ID, new UserPoint(90_000L));

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
        final long 응답_사용자_보유_포인트 = 포인트조회_응답.getLong("balance");

        assertThat(응답_사용자_ID).isEqualTo(1L);
        assertThat(응답_사용자_보유_포인트).isEqualTo(90_000L);
    }

}
