package com.hhp.concert.domain.concert;

import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserRepository;
import com.hhp.concert.infra.concert.ConcertJpaRepository;
import com.hhp.concert.util.DateTimeProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertJpaRepository concertJpaRepository;

    @Mock
    private DateTimeProvider dateTimeProvider;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private ConcertUserRepository concertUserRepository;

    @Mock
    private ConcertReservationRepository concertReservationRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    void 현재_시간에_맞는_콘서트_목록을_조회한다() {
        // given
        final LocalDate 조회_시간 = LocalDate.of(2024, 7, 12);
        given(dateTimeProvider.currentDate())
            .willReturn(조회_시간);
        given(concertRepository.getConcerts(조회_시간))
            .willReturn(List.of(new Concert(
                1L,
                "콘서트1",
                "아티스트1",
                "장소1",
                LocalDate.of(2024, 7, 13),
                LocalDate.of(2024, 7, 14))
            )
        );

        // when
        final List<Concert> 콘서트_목록 = concertService.getConcerts();

        // then
        assertThat(콘서트_목록.size()).isEqualTo(1);
        assertThat(콘서트_목록.get(0).getConcertName()).isEqualTo("콘서트1");
        assertThat(콘서트_목록.get(0).getArtist()).isEqualTo("아티스트1");
        assertThat(콘서트_목록.get(0).getVenue()).isEqualTo("장소1");
    }

    @Test
    void 이미_지난_콘서트_목록은_목록에_없다() {
        // given
        final LocalDate 조회_시간 = LocalDate.of(3000, 1, 1);
        given(dateTimeProvider.currentDate())
            .willReturn(조회_시간);
        given(concertRepository.getConcerts(조회_시간))
            .willReturn(Collections.emptyList());

        // when
        final List<Concert> 콘서트_목록 = concertService.getConcerts();

        // then
        assertThat(콘서트_목록.size()).isEqualTo(0);
    }

    @Test
    void 콘서트_정보를_조회한다() {
        // given
        final long 콘서트_ID = 11L;
        given(concertRepository.getConcertById(콘서트_ID))
            .willReturn(new Concert(
                11L,
                "콘서트1",
                "아티스트1",
                "장소1",
                LocalDate.of(2024, 7, 10),
                LocalDate.of(2024, 7, 20)
            )
        );

        // when
        final Concert 콘서트 = concertService.getConcertById(콘서트_ID);

        // then
        assertThat(콘서트.getId()).isEqualTo(11L);
        assertThat(콘서트.getConcertName()).isEqualTo("콘서트1");
        assertThat(콘서트.getArtist()).isEqualTo("아티스트1");
        assertThat(콘서트.getVenue()).isEqualTo("장소1");
    }

    @Test
    void 콘서트_스케쥴을_조회한다() {
        // given
        final Concert 콘서트 = new Concert(
            11L,
            "콘서트1",
            "아티스트1",
            "장소1",
            LocalDate.of(2024, 7, 10),
            LocalDate.of(2024, 7, 20)
        );

        given(concertScheduleRepository.getConcertSchedulesByConcertId(콘서트))
            .willReturn(List.of(new ConcertSchedule(
                1L,
                콘서트.getId(),
                LocalDate.of(2024, 7, 11),
                LocalDate.of(2024, 7, 10).atTime(13 ,0))
            )
        );

        // when
        final List<ConcertSchedule> 콘서트_스케쥴_목록 = concertScheduleRepository.getConcertSchedulesByConcertId(콘서트);

        // then
        assertThat(콘서트_스케쥴_목록.size()).isEqualTo(1);
    }

    @Test
    void 콘서트_좌석_목록을_조회한다() {
        // given
        final Concert 콘서트 = new Concert(
            11L,
            "콘서트1",
            "아티스트1",
            "장소1",
            LocalDate.of(2024, 7, 10),
            LocalDate.of(2024, 7, 20)
        );

        final ConcertSchedule 스케쥴 = new ConcertSchedule(
            33L,
            11L,
            LocalDate.of(2024, 7, 13),
            LocalDate.of(2024, 7, 13).atTime(13, 0)
        );

        given(concertSeatRepository.getConcertScheduleSeats(콘서트, 스케쥴))
            .willReturn(List.of(new ConcertSeat(
                1L,
                콘서트.getId(),
                스케쥴.getConcertScheduleId(),
                2L,
                "A구역",
                "일반석",
                "A1",
                30_000L,
                true)
            )
        );

        // when
        final List<ConcertSeat> 콘서트_좌석_목록 = concertSeatRepository.getConcertScheduleSeats(콘서트, 스케쥴);

        // then
        assertThat(콘서트_좌석_목록.size()).isEqualTo(1);
        assertThat(콘서트_좌석_목록.get(0).getPrice()).isEqualTo(30_000L);
        assertThat(콘서트_좌석_목록.get(0).getSeatType()).isEqualTo("일반석");
        assertThat(콘서트_좌석_목록.get(0).getZoneName()).isEqualTo("A구역");
        assertThat(콘서트_좌석_목록.get(0).getSeatName()).isEqualTo("A1");
    }

    @Test
    void 콘서트_좌석_정보를_조회한다() {
        // given
        final Concert 콘서트 = new Concert(
                11L,
                "콘서트1",
                "아티스트1",
                "장소1",
                LocalDate.of(2024, 7, 10),
                LocalDate.of(2024, 7, 20)
        );

        final ConcertSchedule 스케쥴 = new ConcertSchedule(
                33L,
                11L,
                LocalDate.of(2024, 7, 13),
                LocalDate.of(2024, 7, 13).atTime(13, 0)
        );

        final ConcertSeat 콘서트_좌석 = new ConcertSeat(
            1L,
            콘서트.getId(),
            스케쥴.getConcertScheduleId(),
            2L,
            "A구역",
            "일반석",
            "A1",
            30_000L,
            true
        );

        given(concertSeatRepository.getConcertSeatById(콘서트_좌석.getConcertSeatId()))
            .willReturn(콘서트_좌석);

        // when
        final ConcertSeat 콘서트_좌석_조회_응답 = concertSeatRepository.getConcertSeatById(콘서트_좌석.getConcertSeatId());

        // then
        assertThat(콘서트_좌석_조회_응답.getPrice()).isEqualTo(30_000L);
        assertThat(콘서트_좌석_조회_응답.getZoneName()).isEqualTo("A구역");
        assertThat(콘서트_좌석_조회_응답.getSeatName()).isEqualTo("A1");
        assertThat(콘서트_좌석_조회_응답.getSeatType()).isEqualTo("일반석");
    }

    @Test
    void 좌석을_예약한다() {
        // given
        final Concert 콘서트 = new Concert(
            11L,
            "콘서트1",
            "아티스트1",
            "장소1",
            LocalDate.of(2024, 7, 13),
            LocalDate.of(2024, 7, 14)
        );

        final ConcertUser 사용자 = new ConcertUser(22L, "사용자", "222@foo.bar");

        final ConcertSchedule 스케쥴 = new ConcertSchedule(
            33L,
            11L,
            LocalDate.of(2024, 7, 13),
            LocalDate.of(2024, 7, 13).atTime(13, 0)
        );

        final ConcertSeat 좌석 = new ConcertSeat(
            1L,
            11L,
            33L,
            44L,
            "A구역",
            "일반석",
            "A1",
            30_000L,
            true
        );

        final ConcertReservation 예약 = new ConcertReservation(
            1L,
            사용자.getId(),
            스케쥴.getConcertScheduleId(),
            좌석.getSeatId(),
            "A1",
            30_000L,
            ConcertReservationStatus.PENDING,
            LocalDate.of(2024, 7, 13).atTime(13, 33, 0)
        );
        given(concertReservationRepository.reserve(사용자, 스케쥴, 좌석))
            .willReturn(예약);

        // when
        final ConcertReservation 예약_응답 = concertReservationRepository.reserve(사용자, 스케쥴, 좌석);

        // then
        assertThat(예약_응답.getPrice()).isEqualTo(30_000L);
        assertThat(예약_응답.getSeatName()).isEqualTo("A1");
        assertThat(예약_응답.getReservationStatus()).isEqualTo(ConcertReservationStatus.PENDING);
    }

}