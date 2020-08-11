package com.woowacourse.pelotonbackend.support;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.woowacourse.pelotonbackend.certification.domain.TimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;

@Component
public class CustomDateParser {
    public List<LocalDate> convertDayToDate(final DateDuration dateDuration, final List<DayOfWeek> days) {
        final LocalDate startTime = dateDuration.getStartDate();
        final LocalDate endTime = dateDuration.getEndDate();
        final List<LocalDate> resultDates = new ArrayList<>();

        for (LocalDate date = startTime; date.isBefore(endTime) || date.isEqual(endTime); date = date.plusDays(1L)) {
            if (days.contains(date.getDayOfWeek())) {
                resultDates.add(date);
            }
        }
        return resultDates;
    }

    public List<DateTimeDuration> convertDateToDuration(final List<LocalDate> dates, final TimeDuration timeDuration) {
        return dates.stream()
            .map(date -> new DateTimeDuration(
                LocalDateTime.of(date, timeDuration.getStartTime()),
                LocalDateTime.of(date, timeDuration.getEndTime())))
            .collect(Collectors.toList());
    }
}
