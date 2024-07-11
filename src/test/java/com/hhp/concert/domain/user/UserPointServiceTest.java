package com.hhp.concert.domain.user;

import com.hhp.concert.domain.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserPointServiceTest {

    @Mock
    private ConcertUserRepository concertUserRepository;

    @Mock
    private UserPointRepository userPointRepository;

    @InjectMocks
    private UserPointService userPointService;

    @Test
    void 포인트를_충전한다() {
        // given
        final long 충전금액 = 100_000L;
        final ConcertUser 사용자 = new ConcertUser(123L, "사용자1", "222@foo.bar");
        final UserPoint 충전하는_포인트 = new UserPoint(충전금액);

        given(concertUserRepository.getUserById(123L))
            .willReturn(사용자);
        given(userPointRepository.charge(사용자, 충전금액))
            .willReturn(new UserPoint(1L, 123L, 100_000L));

        // when
        final UserPoint 충전된_포인트 = userPointService.charge(사용자.getId(), 충전하는_포인트);

        // then
        assertThat(충전된_포인트.getUserId()).isEqualTo(123L);
        assertThat(충전된_포인트.getPoint()).isEqualTo(100_000L);
    }

}
