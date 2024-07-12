package com.hhp.concert.domain.concert;

import com.hhp.concert.util.DateTimeProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final DateTimeProvider dateTimeProvider;

    public ConcertService(
        final ConcertRepository concertRepository,
        final ConcertScheduleRepository concertScheduleRepository,
        final DateTimeProvider dateTimeProvider
    ) {
        this.concertRepository = concertRepository;
        this.concertScheduleRepository = concertScheduleRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    public List<Concert> getConcerts() {
        final LocalDate currentDate = dateTimeProvider.currentDate();

        return concertRepository.getConcerts(currentDate);
    }

    public Concert getConcertById(final long concertId) {
        return concertRepository.getConcertById(concertId);
    }

    public List<ConcertSchedule> getConcertSchedules(final long concertId) {
        final Concert concert = concertRepository.getConcertById(concertId);

        return concertScheduleRepository.getConcertSchedulesByConcertId(concert);
    }

}
