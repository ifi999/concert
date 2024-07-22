package com.hhp.concert.infra.user;

import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.UserPoint;
import com.hhp.concert.domain.user.UserPointRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.infra.user.entity.UserPointEntity;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
import org.springframework.stereotype.Repository;

@Repository
public class UserPointRepositoryImpl implements UserPointRepository {

    private final ConcertUserJpaRepository concertUserJpaRepository;
    private final UserPointJpaRepository userPointJpaRepository;

    public UserPointRepositoryImpl(final ConcertUserJpaRepository concertUserJpaRepository, final UserPointJpaRepository userPointJpaRepository) {
        this.concertUserJpaRepository = concertUserJpaRepository;
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

    @Override
    public UserPoint getBalance(final Long userId) {
        final UserPointEntity userPointEntity = userPointJpaRepository.findByUserId(userId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_POINT_NOT_FOUND));

        return new UserPoint(
            userPointEntity.getId(),
            userPointEntity.getUser().getId(),
            userPointEntity.getPoint()
        );
    }

    @Override
    public UserPoint getUserPointByUserId(final Long userId) {
        final UserPointEntity userPointEntity = userPointJpaRepository.findByUserIdWithLock(userId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_POINT_NOT_FOUND));

        return new UserPoint(
            userPointEntity.getId(),
            userPointEntity.getUser().getId(),
            userPointEntity.getPoint()
        );
    }

    @Override
    public UserPoint updateUserPoint(final UserPoint userPoint) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(userPoint.getUserId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_NOT_FOUND));
        final UserPointEntity userPointEntity = new UserPointEntity(userPoint.getPointId(), userEntity, userPoint.getPoint());

        final UserPointEntity savedUserPointEntity = userPointJpaRepository.save(userPointEntity);

        return new UserPoint(
            savedUserPointEntity.getId(),
            savedUserPointEntity.getUser().getId(),
            savedUserPointEntity.getPoint()
        );
    }

}
