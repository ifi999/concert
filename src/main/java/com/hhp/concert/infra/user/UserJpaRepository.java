package com.hhp.concert.infra.user;

import com.hhp.concert.infra.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}