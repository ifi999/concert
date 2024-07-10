package com.hhp.concert.infra.point;

import com.hhp.concert.domain.point.UserPoint;
import com.hhp.concert.domain.point.UserPointRepository;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.infra.point.entity.UserPointEntity;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserPointRepositoryImpl implements UserPointRepository {

    private final UserPointJpaRepository userPointJpaRepository;

    public UserPointRepositoryImpl(final UserPointJpaRepository userPointJpaRepository) {
        this.userPointJpaRepository = userPointJpaRepository;
    }

    @Override
    public UserPoint charge(final ConcertUser user, final Long point) {
        final ConcertUserEntity userEntity = new ConcertUserEntity(user.getId(), user.getName(), user.getEmail());
        final UserPointEntity userPointEntity = userPointJpaRepository.findByUserIdWithLock(user.getId())
            .orElseGet(() -> userPointJpaRepository.save(new UserPointEntity(userEntity, 0L)));

        userPointEntity.incrementPoint(point);

        return new UserPoint(
            userPointEntity.getId(),
            userPointEntity.getUser().getId(),
            userPointEntity.getPoint()
        );
    }

}
