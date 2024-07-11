package com.hhp.concert.domain.user;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class ConcertUser {

    private Long id;
    private String name;
    private String email;

    public ConcertUser(final String name, final String email) {
        Assert.hasText(name, "User's name must not be empty.");
        Assert.hasText(email, "User's email must not be empty.");

        this.name = name;
        this.email = email;
    }

    public ConcertUser(final Long id, final String name, final String email) {
        Assert.notNull(id, "User's id must not be null.");
        Assert.isTrue(id > 0, "User's id must be positive.");
        Assert.hasText(name, "User's name must not be empty.");
        Assert.hasText(email, "User's email must not be empty.");

        this.id = id;
        this.name = name;
        this.email = email;
    }

}
