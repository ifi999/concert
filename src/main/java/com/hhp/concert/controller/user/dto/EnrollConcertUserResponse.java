package com.hhp.concert.controller.user.dto;

import com.hhp.concert.domain.user.ConcertUser;
import lombok.Getter;

@Getter
public class EnrollConcertUserResponse {

    private Long userId;
    private String name;
    private String email;

    public EnrollConcertUserResponse(final Long userId, final String name, final String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static EnrollConcertUserResponse from(final ConcertUser user) {
        return new EnrollConcertUserResponse(user.getId(), user.getName(), user.getEmail());
    }

}
