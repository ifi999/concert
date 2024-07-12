package com.hhp.concert.domain.concert;

import com.hhp.concert.infra.concert.ConcertJpaRepository;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.util.DateTimeProvider;
import org.assertj.core.api.Assertions;
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

}