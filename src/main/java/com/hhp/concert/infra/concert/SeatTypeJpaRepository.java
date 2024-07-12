package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.SeatTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatTypeJpaRepository extends JpaRepository<SeatTypeEntity, Long> {
}
