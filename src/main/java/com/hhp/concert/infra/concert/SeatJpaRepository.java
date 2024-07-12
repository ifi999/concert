package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {
}
