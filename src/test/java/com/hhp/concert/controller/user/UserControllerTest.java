package com.hhp.concert.controller.user;

import com.hhp.concert.controller.user.dto.EnrollConcertUserRequest;
import com.hhp.concert.controller.user.dto.GetTokenRequest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    @Test
    void 토큰을_발급받는다() {
        // given
        final long 사용자_ID = 1L;
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
        final String 응답_사용자_토큰 = 토큰발급_응답.getString("token");

        assertThat(응답_사용자_ID).isEqualTo(123L);
        assertThat(응답_사용자_토큰).isEqualTo("token");
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

}
