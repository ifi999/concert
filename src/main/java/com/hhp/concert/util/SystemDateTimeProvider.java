package com.hhp.concert.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public LocalDate currentDate() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

}
