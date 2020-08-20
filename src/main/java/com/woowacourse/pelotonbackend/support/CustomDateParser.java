package com.woowacourse.pelotonbackend.support;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.woowacourse.pelotonbackend.certification.domain.TimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;

@Component
public class CustomDateParser {

    private static final int A_DAY_IN_MINUTES = 1440;

    public List<LocalDate> convertDayToDate(final DateDuration dateDuration, final List<DayOfWeek> days) {
        final LocalDate startDate = dateDuration.getStartDate();
        final LocalDate endDate = dateDuration.getEndDate();
        final List<LocalDate> resultDates = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1L)) {
            if (days.contains(date.getDayOfWeek())) {
                resultDates.add(date);
            }
        }
        return resultDates;
    }

    public List<DateTimeDuration> convertDateToDuration(final List<LocalDate> dates, final TimeDuration timeDuration) {
        final long differenceBetweenDuration = calculateDifferenceInMinutes(timeDuration);
        return dates.stream()
            .map(date -> {
                final LocalDateTime startDateTime = LocalDateTime.of(date, timeDuration.getStartTime());
                final LocalDateTime endDateTime = startDateTime.plus(differenceBetweenDuration, ChronoUnit.MINUTES);
                return new DateTimeDuration(startDateTime, endDateTime);
            })
            .collect(Collectors.toList());
    }

    private long calculateDifferenceInMinutes(final TimeDuration timeDuration) {
        final LocalTime startTime = timeDuration.getStartTime();
        final LocalTime endTime = timeDuration.getEndTime();

        final long differenceInMinutes = ChronoUnit.MINUTES.between(startTime, endTime);
        if (differenceInMinutes < 0) {
            return A_DAY_IN_MINUTES + differenceInMinutes;
        }
        return differenceInMinutes;
    }

}
