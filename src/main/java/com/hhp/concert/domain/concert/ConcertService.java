package com.hhp.concert.domain.concert;

import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.util.DateTimeProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final DateTimeProvider dateTimeProvider;

    public ConcertService(final ConcertRepository concertRepository, final DateTimeProvider dateTimeProvider) {
        this.concertRepository = concertRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    public List<Concert> getConcerts() {
        final LocalDate currentDate = dateTimeProvider.currentDate();
        List<ConcertEntity> concertEntities = concertRepository.getConcerts(currentDate);

        return concertEntities.stream()
            .map(o -> Concert.builder()
                .id(o.getId())
                .concertName(o.getConcertName())
                .artist(o.getArtist())
                .venue(o.getVenue())
                .startDate(o.getStartDate())
                .endDate(o.getEndDate())
                .build())
            .toList();
    }

}
