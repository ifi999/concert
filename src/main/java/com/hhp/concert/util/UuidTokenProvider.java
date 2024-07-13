package com.hhp.concert.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidTokenProvider implements TokenProvider {

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}
