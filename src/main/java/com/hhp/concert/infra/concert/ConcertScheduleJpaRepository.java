package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {

    List<ConcertScheduleEntity> findByConcert(ConcertEntity concert);

}
