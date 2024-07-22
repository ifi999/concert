package com.hhp.concert.domain.token;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    @Test
    void 토큰을_발급한다() {
        // given
        final long 사용자_ID = 123L;
        given(tokenRepository.getTokenByUserId(사용자_ID))
            .willReturn(new Token(
                1L,
                123L,
                "token",
                0L,
                LocalDateTime.of(2024, 7, 13, 10, 15, 23)
            )
        );

        // when
        final Token 토큰 = tokenService.getToken(new Token(사용자_ID));

        // then
        assertThat(토큰.getUserId()).isEqualTo(123L);
        assertThat(토큰.getToken()).isEqualTo("token");
        assertThat(토큰.getQueueNumber()).isEqualTo(0L);
    }

    @Test
    void 토큰_갱신_시_대기열_번호가_0이라면_토큰이_활성화_된다() {
        // given
        given(tokenRepository.getPendingToken(1L))
            .willReturn(new Token(
                1L,
                123L,
                "token",
                0L,
                LocalDateTime.of(2024, 7, 13, 10, 15, 23)
            )
        );

        given(tokenRepository.getOldestPendingToken())
            .willReturn(List.of(1L));

        // when
        final Token 토큰 = tokenService.renewToken(1L);

        // then
        assertThat(토큰.getUserId()).isEqualTo(123L);
        assertThat(토큰.getQueueNumber()).isEqualTo(0L);
    }

}
