package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.SeatZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatZoneJpaRepository extends JpaRepository<SeatZoneEntity, Long> {
}
