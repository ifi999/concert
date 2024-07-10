package com.hhp.concert.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConcertUserTest {

    @Test
    void 생성자의_이름은_빈문자열이_될_수_없다() {
        // given
        final String 빈문자열 = "";
        final String 메일 = "222@foo.bar";

        // when

        // then
        assertThatThrownBy(() -> new ConcertUser(빈문자열, 메일))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User's name must not be empty.");

        assertThatThrownBy(() -> new ConcertUser(null, 메일))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User's name must not be empty.");

        assertThatThrownBy(() -> new ConcertUser(1L, 빈문자열, 메일))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User's name must not be empty.");

        assertThatThrownBy(() -> new ConcertUser(1L, null, 메일))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User's name must not be empty.");
    }

    @Test
    void 생성자의_이메일은_빈문자열이_될_수_없다() {
        // given
        final String 이름 = "사용자1";
        final String 빈문자열 = "";

        // when & then
        assertThatThrownBy(() -> new ConcertUser(이름, 빈문자열))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's email must not be empty.");

        assertThatThrownBy(() -> new ConcertUser(이름, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's email must not be empty.");

        assertThatThrownBy(() -> new ConcertUser(1L, 이름, 빈문자열))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's email must not be empty.");

        assertThatThrownBy(() -> new ConcertUser(1L, 이름, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's email must not be empty.");
    }

    @Test
    void 생성자의_id는_양수가_아니면_안된다() {
        // given
        final Long 음수아이디 = -1L;
        final String 이름 = "사용자1";
        final String 메일 = "222@foo.bar";

        // when & then
        assertThatThrownBy(() -> new ConcertUser(음수아이디, 이름, 메일))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's id must be positive.");

        assertThatThrownBy(() -> new ConcertUser(null, 이름, 메일))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User's id must not be null.");
    }

}
