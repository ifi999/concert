package com.hhp.concert.domain.point;

import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserPointService {

    private final UserPointRepository userPointRepository;
    private final ConcertUserRepository concertUserRepository;

    public UserPointService(final UserPointRepository userPointRepository, final ConcertUserRepository concertUserRepository) {
        this.userPointRepository = userPointRepository;
        this.concertUserRepository = concertUserRepository;
    }

    public UserPoint charge(final UserPoint userPoint) {
        final ConcertUser user = concertUserRepository.getUserById(userPoint.getUserId());

        return userPointRepository.charge(user, userPoint.getPoint());
    }

}
