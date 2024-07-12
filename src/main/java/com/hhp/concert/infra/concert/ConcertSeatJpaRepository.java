package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.infra.concert.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

    List<ConcertSeatEntity> findByConcertAndConcertSchedule(ConcertEntity concert, ConcertScheduleEntity schedule);

}
