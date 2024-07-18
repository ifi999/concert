package com.hhp.concert.domain.concert;

import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserRepository;
import com.hhp.concert.util.DateTimeProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertUserRepository concertUserRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final DateTimeProvider dateTimeProvider;

    public ConcertService(
        final ConcertRepository concertRepository,
        final ConcertScheduleRepository concertScheduleRepository,
        final ConcertSeatRepository concertSeatRepository,
        final ConcertUserRepository concertUserRepository,
        final ConcertReservationRepository concertReservationRepository,
        final DateTimeProvider dateTimeProvider
    ) {
        this.concertRepository = concertRepository;
        this.concertScheduleRepository = concertScheduleRepository;
        this.concertSeatRepository = concertSeatRepository;
        this.concertUserRepository = concertUserRepository;
        this.concertReservationRepository = concertReservationRepository;
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

    public List<ConcertSeat> getConcertScheduleSeats(final long concertId, final long scheduleId) {
        final Concert concert = concertRepository.getConcertById(concertId);
        final ConcertSchedule concertSchedule = concertScheduleRepository.getConcertScheduleById(scheduleId);

        return concertSeatRepository.getConcertScheduleSeats(concert, concertSchedule);
    }

    public ConcertSeat getSeatInfo(final long concertId, final long scheduleId, final long seatId) {
        final Concert concert = concertRepository.getConcertById(concertId);
        final ConcertSchedule schedule = concertScheduleRepository.getConcertScheduleById(scheduleId);

        return concertSeatRepository.getSeatInfo(concert, schedule, seatId);
    }

    public ConcertReservation reserve(final ConcertReservation reservation) {
        concertRepository.getConcertById(reservation.getConcertId());
        final ConcertUser user = concertUserRepository.getUserById(reservation.getUserId());
        final ConcertSchedule schedule = concertScheduleRepository.getConcertScheduleById(reservation.getScheduleId());
        final ConcertSeat seat = concertSeatRepository.getConcertSeatById(reservation.getSeatId());
        seat.reserve();
        concertSeatRepository.updateSeat(seat);

        return concertReservationRepository.reserve(user, schedule, seat);
    }

}
