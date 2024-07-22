package com.hhp.concert.infra.payment;

import com.hhp.concert.infra.payment.entity.PaymentEntity;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findAllByUser(ConcertUserEntity user);

}
