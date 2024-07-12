package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.ConcertReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertReservationJpaRepository extends JpaRepository<ConcertReservationEntity, Long> {
}
