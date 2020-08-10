package com.woowacourse.pelotonbackend.support;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomDateParserTest {
    private CustomDateParser dateParser;

    @BeforeEach
    void setUp() {
        dateParser = new CustomDateParser();
    }

    /**
     *  2020 / August
     *  SUN | MON | TUE | WED | THUR | FRI | SAT
     *                                        1
     *  *2* | *3* | *4* |  5  |  6   |  7  |  8
     *  *9* |*10* |*11* | 12  |  13  | 14  | 15
         * *16* |*17* |*18* | 19  |  20  | 21  | 22
         * *23* |*24* |*25* | 26  |  27  | 28  | 29
         * *30* |*31*
         */
        @DisplayName("요일과 시작, 종료 날짜가 주어졌을 때 이를 날짜의 list로 변환하는 테스트")
        @Test
        void dayToDate() {
        List<DayOfWeek> days = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        LocalDate startTime = LocalDate.of(2020, 8, 1);
        LocalDate endTime = LocalDate.of(2020, 8, 31);

        List<LocalDate> resultDates = dateParser.convertDayToDate(startTime, endTime, days);

        assertThat(resultDates.stream().map(LocalDate::getDayOfMonth))
            .containsExactly(2, 3, 4, 9, 10, 11, 16, 17, 18, 23, 24, 25, 30, 31);
    }
}
