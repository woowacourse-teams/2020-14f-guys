package com.woowacourse.pelotonbackend.support;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomDateParser {
    public List<LocalDate> convertDayToDate(final LocalDate startTime, final LocalDate endTime,
        final List<DayOfWeek> days) {
        List<LocalDate> resultDates = new ArrayList<>();

        for (LocalDate date = startTime; date.isBefore(endTime) || date.isEqual(endTime); date = date.plusDays(1L)) {
            if (days.contains(date.getDayOfWeek())) {
                resultDates.add(date);
            }
        }

        return resultDates;
    }
}
