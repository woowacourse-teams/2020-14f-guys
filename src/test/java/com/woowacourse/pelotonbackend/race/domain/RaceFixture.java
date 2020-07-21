package com.woowacourse.pelotonbackend.race.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.woowacourse.pelotonbackend.race.presentation.RaceCreateRequest;
import com.woowacourse.pelotonbackend.race.presentation.RaceRetrieveResponse;
import com.woowacourse.pelotonbackend.race.presentation.RaceUpdateRequest;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class RaceFixture {
    public static final Long TEST_RACE_ID = 1L;
    public static final String TEST_TITLE = "14층 녀석들 기상 레이스";
    public static final String TEST_CHANGED_TITLE = "14층 녀석들 지각 안하기 레이스";
    public static final String TEST_DESCRIPTION = "아침 6시에 일어나보자!";
    public static final ImageUrl TEST_THUMBNAIL_URL = new ImageUrl(
        "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng");
    public static final ImageUrl TEST_CERTIFICATION_URL = new ImageUrl(
        "data:image/png;base64,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");
    public static final BigDecimal TEST_MONEY_AMOUNT = new BigDecimal(20000);
    public static final BigDecimal TEST_CHANGED_MONEY_AMOUNT = new BigDecimal(25000);
    public static final LocalDate TEST_START_TIME = LocalDate.of(2030, 1, 1);
    public static final LocalDate TEST_END_TIME = LocalDate.of(2031, 1, 1);
    public static final RaceCategory TEST_CATEGORY = RaceCategory.TIME;

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
            .entranceFee(new Cash(TEST_CHANGED_MONEY_AMOUNT))
            .category(TEST_CATEGORY)
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .description(TEST_DESCRIPTION)
            .certificationExample(TEST_CERTIFICATION_URL)
            .thumbnail(TEST_THUMBNAIL_URL)
            .build();
    }

    public static RaceCreateRequest createMockRequest() {
        return RaceCreateRequest.builder()
            .title(TEST_TITLE)
            .description(TEST_DESCRIPTION)
            .category(TEST_CATEGORY)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();
    }

    public static RaceCreateRequest createBadMockRequest() {
        return RaceCreateRequest.builder()
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();
    }

    public static RaceRetrieveResponse retrieveResponse() {
        return RaceRetrieveResponse.of(createWithId(TEST_RACE_ID));
    }

    public static RaceUpdateRequest updateRequest() {
        return RaceUpdateRequest.builder()
            .title(TEST_CHANGED_TITLE)
            .entranceFee(new Cash(TEST_CHANGED_MONEY_AMOUNT))
            .build();
    }

    public static RaceRetrieveResponse retrieveUpdatedResponse() {
        return RaceRetrieveResponse.of(createUpdatedRace());
    }
}