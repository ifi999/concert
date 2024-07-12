package com.hhp.concert.domain.concert;

import java.time.LocalDate;
import java.util.List;

public interface ConcertRepository {

    List<Concert> getConcerts(LocalDate currentDate);

    Concert getConcertById(long concertId);

}
