package com.woowacourse.pelotonbackend.race.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.woowacourse.pelotonbackend.race.presentation.RaceCreateRequest;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class RaceFixture {
    public static final String TEST_TITLE = "14층 녀석들 기상 레이스";
    public static final String TEST_DESCRIPTION = "아침 6시에 일어나보자!";
    public static final String TEST_IMAGE_URL = "https://abc.com";
    public static final BigDecimal TEST_MONEY_AMOUNT = new BigDecimal(20000);
    public static final LocalDate TEST_START_TIME = LocalDate.of(2030, 1, 1);
    public static final LocalDate TEST_END_TIME = LocalDate.of(2031, 1, 1);
    public static final RaceCategory TEST_CATEGORY = RaceCategory.TIME;

    public static Race createWithoutId() {
        return Race.builder()
            .category(TEST_CATEGORY)
            .title(TEST_TITLE)
            .description(TEST_DESCRIPTION)
            .certificationExample(new ImageUrl(TEST_IMAGE_URL))
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .thumbnail(new ImageUrl(TEST_IMAGE_URL))
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();
    }

    public static Race createWithId() {
        return Race.builder()
            .id(1L)
            .title(TEST_TITLE)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .category(TEST_CATEGORY)
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .description(TEST_DESCRIPTION)
            .build();
    }

    public static RaceCreateRequest createMockRequest() {
        return RaceCreateRequest.builder()
            .title(TEST_TITLE)
            .description(TEST_DESCRIPTION)
            .category(TEST_CATEGORY)
            .cash(new Cash(BigDecimal.ONE))
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();
    }

    public static RaceCreateRequest createBadMockRequest() {
        return RaceCreateRequest.builder()
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();
    }
}