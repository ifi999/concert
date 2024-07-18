package com.hhp.concert.controller.token;

import com.hhp.concert.controller.token.dto.GetTokenRequest;
import com.hhp.concert.infra.token.TokenJpaRepository;
import com.hhp.concert.infra.token.entity.TokenEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TokenControllerTest {

    @Autowired
    private ConcertUserJpaRepository concertUserJpaRepository;

    @Autowired
    private TokenJpaRepository tokenJpaRepository;

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

        assertThat(응답_사용자_ID).isEqualTo(사용자_ID);
    }

    @Test
    void 토큰을_갱신한다() {
        // given
        final ConcertUserEntity 사용자_엔티티 = concertUserJpaRepository.save(new ConcertUserEntity("사용자1", "222@foo.bar"));

        final TokenEntity 토큰 = tokenJpaRepository.save(new TokenEntity(사용자_엔티티, "token", LocalDateTime.now()));
        final Long 토큰_ID = 토큰.getId();

        // when
        final JsonPath 토큰갱신_응답 =
            given()
                .log().all()
            .when()
                .patch("/api/tokens/{tokenId}", 토큰_ID)
            .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
            .extract()
                .jsonPath();

        // then
        final String 응답_토큰 = 토큰갱신_응답.getString("token");

        assertThat(응답_토큰).isEqualTo("token");
    }

}
