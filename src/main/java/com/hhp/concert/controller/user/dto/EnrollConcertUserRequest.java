package com.hhp.concert.controller.user.dto;

import com.hhp.concert.domain.user.ConcertUser;
import lombok.Getter;

@Getter
public class EnrollConcertUserRequest {

    private String name;
    private String email;

    public EnrollConcertUserRequest(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public ConcertUser toDomain() {
        return new ConcertUser(this.name, this.email);
    }
}
