package com.hhp.concert.domain.point;

import com.hhp.concert.domain.user.ConcertUser;

public interface UserPointRepository {

    UserPoint charge(ConcertUser user, Long point);

}
