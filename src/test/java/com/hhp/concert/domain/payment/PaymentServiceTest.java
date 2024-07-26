package com.hhp.concert.domain.payment;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.concert.ConcertReservationRepository;
import com.hhp.concert.domain.concert.ConcertReservationStatus;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserRepository;
import com.hhp.concert.domain.user.UserPoint;
import com.hhp.concert.domain.user.UserPointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private ConcertReservationRepository concertReservationRepository;

    @Mock
    private ConcertUserRepository concertUserRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserPointRepository userPointRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void 결제한다() {
        // given
        final ConcertUser 사용자 = new ConcertUser(22L, "사용자", "222@foo.bar");
        given(concertUserRepository.getUserById(any()))
            .willReturn(사용자);

        given(userPointRepository.getUserPointByUserId(22L))
            .willReturn(new UserPoint(1L, 22L, 50_000L, 0L));

        final ConcertReservation 예약 = new ConcertReservation(
            1L,
            사용자.getId(),
            2L,
            2L,
            3L,
            "A1",
            30_000L,
            ConcertReservationStatus.PENDING,
            LocalDate.of(2024, 7, 13).atTime(13, 33, 0)
        );
        given(concertReservationRepository.getReservationById(any()))
            .willReturn(예약);

        given(concertReservationRepository.updateConcertReservation(예약))
            .willReturn(ConcertReservation.builder()
                .reservationId(99L)
                .userId(사용자.getId())
                .concertId(2L)
                .scheduleId(2L)
                .seatId(3L)
                .seatName("A1")
                .price(30_000L)
                .reservationStatus(ConcertReservationStatus.PENDING)
                .createdAt(LocalDateTime.of(2024, 7, 13, 9, 8, 12))
                .build()
        );

        given(paymentRepository.pay(any(ConcertUser.class), any(ConcertReservation.class), eq(30_000L)))
            .willReturn(Payment.builder()
                .paymentId(11L)
                .reservationId(예약.getReservationId())
                .userId(사용자.getId())
                .paymentAmount(30_000L)
                .createdAt(LocalDateTime.of(2024, 7, 13, 9, 8, 12))
                .build()
        );

        // when
        final Payment 예약_응답 = paymentService.pay(new Payment(예약.getReservationId(), 사용자.getId(), 30_000L));

        // then
        assertThat(예약_응답.getPaymentAmount()).isEqualTo(30_000L);
    }

    @Test
    void 사용자의_결제_목록을_조회한다() {
        // given
        final ConcertUser 사용자 = new ConcertUser(22L, "사용자", "222@foo.bar");
        final ConcertReservation 예약 = new ConcertReservation(
            1L,
            사용자.getId(),
            2L,
            2L,
            3L,
            "A1",
            30_000L,
            ConcertReservationStatus.PENDING,
            LocalDate.of(2024, 7, 13).atTime(13, 33, 0)
        );

        given(paymentRepository.getUserPayments(사용자))
            .willReturn(List.of(Payment.builder()
                .paymentId(11L)
                .reservationId(예약.getReservationId())
                .userId(사용자.getId())
                .paymentAmount(30_000L)
                .createdAt(LocalDateTime.of(2024, 7, 13, 9, 8, 12))
                .build())
            );

        // when
        final List<Payment> 결제_목록 = paymentRepository.getUserPayments(사용자);

        // then
        assertThat(결제_목록.size()).isEqualTo(1);
        assertThat(결제_목록.get(0).getPaymentAmount()).isEqualTo(30_000L);
    }

}
