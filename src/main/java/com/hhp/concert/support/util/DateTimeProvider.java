package com.hhp.concert.support.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateTimeProvider {

    LocalDate currentDate();

    LocalDateTime currentDateTime();

}
