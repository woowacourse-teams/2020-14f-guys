package com.woowacourse.pelotonbackend.race.domain;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceUpdateRequest;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class RaceFixture {
    public static final Long TEST_RACE_ID = 1L;
    public static final Long TEST_RACE_ID2 = 2L;
    public static final String TEST_TITLE = "14층 녀석들 기상 레이스";
    public static final String TEST_CHANGED_TITLE = "14층 녀석들 지각 안하기 레이스";
    public static final String TEST_DESCRIPTION = "아침 6시에 일어나보자!";
    public static final String TEST_CHANGED_DESCRIPTION = "10시 데일리에 늦지 않고 참가해보자!";
    public static final ImageUrl TEST_THUMBNAIL_URL = new ImageUrl(
        "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng");
    public static final ImageUrl TEST_CHANGED_THUMBNAIL_URL = new ImageUrl(
        "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6V");
    public static final ImageUrl TEST_CERTIFICATION_URL = new ImageUrl(
        "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng");
    public static final ImageUrl TEST_CHANGED_CERTIFICATION_URL = new ImageUrl(
        "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6V");
    public static final BigDecimal TEST_MONEY_AMOUNT = new BigDecimal(20000);
    public static final BigDecimal TEST_CHANGED_MONEY_AMOUNT = new BigDecimal(25000);
    public static final LocalDate TEST_START_TIME = LocalDate.now().plusDays(1L);
    public static final LocalDate TEST_START_TIME_PAST = LocalDate.now().minusDays(2L);
    public static final LocalDate TEST_CHANGED_START_TIME = LocalDate.now().plusDays(2L);
    public static final LocalDate TEST_END_TIME = LocalDate.now().plusDays(2L);
    public static final LocalDate TEST_END_TIME_PAST = LocalDate.now().minusDays(1L);
    public static final LocalDate TEST_CHANGED_END_TIME = LocalDate.now().plusDays(3L);
    public static final RaceCategory TEST_CATEGORY = RaceCategory.TIME;
    public static final RaceCategory TEST_CHANGED_CATEGORY = RaceCategory.STUDY;
    public static final String RACE_API_URL = "/api/races";

    public static Race createWithoutId() {
        return createWithId(null);
    }

    public static Race createWithId(final Long id) {
        return Race.builder()
            .id(id)
            .title(TEST_TITLE)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .category(TEST_CATEGORY)
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .description(TEST_DESCRIPTION)
            .certificationExample(TEST_CERTIFICATION_URL)
            .thumbnail(TEST_THUMBNAIL_URL)
            .build();
    }

    public static Race createWithIdAndPast(final Long id) {
        return Race.builder()
            .id(id)
            .title(TEST_TITLE)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .category(TEST_CATEGORY)
            .raceDuration(new DateDuration(TEST_START_TIME_PAST, TEST_END_TIME_PAST))
            .description(TEST_DESCRIPTION)
            .certificationExample(TEST_CERTIFICATION_URL)
            .thumbnail(TEST_THUMBNAIL_URL)
            .build();
    }

    public static Race createWithUrls(ImageUrl certification, ImageUrl thumbnail) {
        return Race.builder()
            .id(TEST_RACE_ID)
            .title(TEST_TITLE)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .category(TEST_CATEGORY)
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .description(TEST_DESCRIPTION)
            .certificationExample(certification)
            .thumbnail(thumbnail)
            .build();
    }

    public static Race createUpdatedRace() {
        return Race.builder()
            .id(TEST_RACE_ID)
            .title(TEST_CHANGED_TITLE)
            .description(TEST_CHANGED_DESCRIPTION)
            .raceDuration(new DateDuration(TEST_CHANGED_START_TIME, TEST_CHANGED_END_TIME))
            .entranceFee(new Cash(TEST_CHANGED_MONEY_AMOUNT))
            .category(TEST_CHANGED_CATEGORY)
            .certificationExample(TEST_CHANGED_CERTIFICATION_URL)
            .thumbnail(TEST_CHANGED_THUMBNAIL_URL)
            .build();
    }

    public static RaceCreateRequest createMockRequest() {
        return RaceCreateRequest.builder()
            .title(TEST_TITLE)
            .description(TEST_DESCRIPTION)
            .category(TEST_CATEGORY)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .days(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY))
            .certificationAvailableDuration(MissionFixture.timeDurationFixture())
            .build();
    }

    public static RaceCreateRequest createFinishedRequest() {
        return RaceCreateRequest.builder()
            .title(TEST_TITLE)
            .description(TEST_DESCRIPTION)
            .category(TEST_CATEGORY)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .raceDuration(new DateDuration(TEST_START_TIME_PAST, TEST_END_TIME_PAST))
            .days(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY))
            .certificationAvailableDuration(MissionFixture.timeDurationFixture())
            .build();
    }

    public static RaceCreateRequest createBadMockRequest() {
        return RaceCreateRequest.builder()
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();
    }

    public static RaceResponse retrieveResponse() {
        return RaceResponse.of(createWithId(TEST_RACE_ID));
    }

    public static RaceResponse retrieveFinishedResponse() {
        return RaceResponse.of(createWithIdAndPast(TEST_RACE_ID));
    }

    public static RaceResponse retrieveNotFinishedResponse() {
        return RaceResponse.of(createWithId(TEST_RACE_ID));
    }

    public static RaceUpdateRequest updateRequest() {
        return RaceUpdateRequest.builder()
            .title(TEST_CHANGED_TITLE)
            .category(TEST_CHANGED_CATEGORY)
            .certification(TEST_CHANGED_CERTIFICATION_URL)
            .description(TEST_CHANGED_DESCRIPTION)
            .raceDuration(new DateDuration(TEST_CHANGED_START_TIME, TEST_CHANGED_END_TIME))
            .thumbnail(TEST_CHANGED_THUMBNAIL_URL)
            .entranceFee(new Cash(TEST_CHANGED_MONEY_AMOUNT))
            .build();
    }

    public static RaceResponse retrieveUpdatedResponse() {
        return RaceResponse.of(createUpdatedRace());
    }
}
