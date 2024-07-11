package com.hhp.concert.domain.point;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserPointTest {

    @Test
    void 생성자의_사용자_id는_양수가_아니면_안된다() {
        // given
        final Long 음수_사용자_id = -1L;
        final Long 포인트 = 100_000L;

        // when

        // then
        assertThatThrownBy(() -> new UserPoint(음수_사용자_id, 포인트))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's id must be positive.");

        assertThatThrownBy(() -> new UserPoint(null, 포인트))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's id must not be null.");
    }

    @Test
    void 생성자의_포인트는_양수가_아니면_안된다() {
        // given
        final Long 사용자_id = 123L;
        final Long 음수_포인트 = -100_000L;

        // when

        // then
        assertThatThrownBy(() -> new UserPoint(사용자_id, 음수_포인트))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Point must be positive.");

        assertThatThrownBy(() -> new UserPoint(사용자_id, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Point must not be null.");
    }

    @Test
    void 생성자의_id는_양수가_아니면_안된다() {
        // given
        final Long 음수_포인트_id = -1L;
        final Long 사용자_id = 123L;
        final Long 포인트 = 100_000L;

        // when

        // then
        assertThatThrownBy(() -> new UserPoint(음수_포인트_id, 사용자_id, 포인트))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Point's id must be positive.");

        assertThatThrownBy(() -> new UserPoint(null, 사용자_id, 포인트))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Point's id must not be null.");
    }

}