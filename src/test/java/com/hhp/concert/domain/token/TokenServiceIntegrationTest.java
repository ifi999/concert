package com.hhp.concert.domain.token;

import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserService;
import com.hhp.concert.infra.token.TokenJpaRepository;
import com.hhp.concert.infra.token.entity.TokenEntity;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.util.DateTimeProvider;
import com.hhp.concert.util.TokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenServiceIntegrationTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ConcertUserService concertUserService;

    @Autowired
    private TokenJpaRepository tokenJpaRepository;

    @Autowired
    private DateTimeProvider dateTimeProvider;

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void 대기중인_토큰이_있다면_그대로_반환된다() {
        // given
        final ConcertUser 사용자 = concertUserService.enroll(new ConcertUser("사용자1", "222@foo.bar"));
        final Token 토큰 = tokenService.getToken(new Token(사용자.getId()));

        // when
        final Token 재요청_토큰 = tokenService.getToken(new Token(사용자.getId()));

        // then
        assertThat(토큰.getTokenId()).isEqualTo(재요청_토큰.getTokenId());
    }

    @Test
    void 대기시간이_만료되었다면_새_토큰을_발행한다() {
        // given
        final ConcertUser 사용자 = concertUserService.enroll(new ConcertUser("사용자1", "222@foo.bar"));
        final ConcertUserEntity 사용자_엔티티 = new ConcertUserEntity(사용자.getId(), 사용자.getName(), 사용자.getEmail());

        final LocalDateTime 현재시간 = dateTimeProvider.currentDateTime();
        final String UUID_토큰 = tokenProvider.generateToken();
        final TokenEntity 기존_토큰 = tokenJpaRepository.save(new TokenEntity(사용자_엔티티, UUID_토큰, TokenStatus.PENDING, 현재시간.minusMinutes(10)));
        System.out.println("기존_토큰.getToken() = " + 기존_토큰.getToken());

        // when
        final Token 재요청_토큰 = tokenService.getToken(new Token(사용자.getId()));
        System.out.println("재요청_토큰.getToken() = " + 재요청_토큰.getToken());

        // then
        assertThat(기존_토큰.getId()).isNotEqualTo(재요청_토큰.getTokenId());
    }

}
