package com.hhp.concert.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConcertUserServiceTest {

    @Mock
    private UserPointRepository userPointRepository;

    @Mock
    private ConcertUserRepository concertUserRepository;

    @InjectMocks
    private ConcertUserService concertUserService;

    @Test
    void 사용자를_등록한다() {
        // given
        final String 사용자_이름 = "사용자1";
        final String 사용자_메일 = "222@foo.bar";
        final ConcertUser 사용자 = new ConcertUser(사용자_이름, 사용자_메일);
        given(concertUserRepository.enroll(사용자))
            .willReturn(new ConcertUser(1L, "사용자1", "222@foo.bar"));
        given(userPointRepository.getUserPoint(any()))
            .willReturn(new UserPoint(1L, 1L, 0L, 0L));

        // when
        final ConcertUser 등록된_사용자 = concertUserService.enroll(사용자);

        // then
        assertThat(등록된_사용자.getId()).isPositive();
        assertThat(등록된_사용자.getName()).isEqualTo("사용자1");
        assertThat(등록된_사용자.getEmail()).isEqualTo("222@foo.bar");
    }

}
