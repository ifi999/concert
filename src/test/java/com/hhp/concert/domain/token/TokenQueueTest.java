package com.hhp.concert.domain.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TokenQueueTest {

    @BeforeEach
    void setUp() {
        TokenQueue.cleanQueue();
    }

    @Test
    void 입장_토큰_큐에_토큰을_추가한다() {
        // given
        final Token 토큰 = new Token(123L, 456L, "token", 0L, LocalDateTime.now());

        // when
        TokenQueue.add(토큰);

        // then
        final int 여분_토큰_큐_사이즈 = TokenQueue.getExtraQueueSpaceSize();

        assertThat(여분_토큰_큐_사이즈).isEqualTo(99);
    }

    @Test
    void 입장_토큰_큐가_가득차면_더이상_추가되지_않는다() {
        // given
        for (int i = 0; i < 100; i++) {
            TokenQueue.add(new Token((long) i, (long) i, "token" + i, 0L, LocalDateTime.now()));
        }

        // when
        final Token 추가_토큰 = new Token(123L, 456L, "token", 0L, LocalDateTime.now());
        TokenQueue.add(추가_토큰);

        // then
        final int 여분_토큰_큐_사이즈 = TokenQueue.getExtraQueueSpaceSize();

        assertThat(여분_토큰_큐_사이즈).isEqualTo(0);
    }

    @Test
    void 중복된_토큰ID는_큐에_들어가지_않는다() {
        // given
        final Token 토큰 = new Token(123L, 456L, "token", 0L, LocalDateTime.now());
        TokenQueue.add(토큰);

        // when
        TokenQueue.add(토큰);

        // then
        final int 여분_토큰_큐_사이즈 = TokenQueue.getExtraQueueSpaceSize();

        assertThat(여분_토큰_큐_사이즈).isEqualTo(99);
    }

}
