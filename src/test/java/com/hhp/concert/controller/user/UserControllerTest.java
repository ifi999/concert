package com.hhp.concert.controller.user;

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

}
