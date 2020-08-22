package com.woowacourse.pelotonbackend.support;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.pelotonbackend.certification.domain.TimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;

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
        final List<DayOfWeek> days = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        final LocalDate startDate = LocalDate.of(2020, 8, 1);
        final LocalDate endDate = LocalDate.of(2020, 8, 31);

        final List<LocalDate> resultDates = dateParser.convertDayToDate(new DateDuration(startDate, endDate), days);

        assertThat(resultDates.stream().map(LocalDate::getDayOfMonth))
            .containsExactly(2, 3, 4, 9, 10, 11, 16, 17, 18, 23, 24, 25, 30, 31);
    }

    @DisplayName("날짜들과 시간 구간이 주어졌을 때, 둘을 합쳐서 날짜+시간의 구간이 나오는지 테스트")
    @ParameterizedTest
    @MethodSource("generateTimes")
    void dateToDuration(LocalTime startTime, LocalTime endTime) {
        List<LocalDate> dates = MissionFixture.datesFixture();

        List<DateTimeDuration> results = dateParser.convertDateToDuration(dates, new TimeDuration(startTime, endTime));

        assertAll(
            () -> assertThat(results).extracting(dateTime -> dateTime.getStartTime().toLocalDate())
                .containsExactlyInAnyOrderElementsOf(dates),
            () -> assertThat(results).extracting(dateTime -> dateTime.getStartTime().toLocalTime())
                .containsOnly(startTime),
            () -> assertThat(results).extracting(dateTime -> dateTime.getEndTime().toLocalTime())
                .containsOnly(endTime)
        );
    }

    private static Stream<Arguments> generateTimes() {
        return Stream.of(
            Arguments.of(LocalTime.of(7, 0), LocalTime.of(9, 30)),
            Arguments.of(LocalTime.of(23, 0), LocalTime.of(23, 5)),
            Arguments.of(LocalTime.of(1, 0), LocalTime.of(23, 30)),
            Arguments.of(LocalTime.of(23, 0), LocalTime.of(1, 30)),
            Arguments.of(LocalTime.of(23, 0), LocalTime.of(22, 30))
        );
    }
}
