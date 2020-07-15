package com.woowacourse.pelotonbackend.race.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

@SpringBootTest
public class RaceRepositoryTest {
    public static final String TEST_TITLE = "14층 녀석들 기상 레이스";
    public static final String TEST_DESCRIPTION = "아침 6시에 일어나보자!";
    public static final String TEST_IMAGE_URL = "https://abc.com";
    public static final BigDecimal TEST_MONEY_AMOUNT = new BigDecimal(20000);
    public static final LocalDate TEST_START_TIME = LocalDate.of(2030, 1, 1);
    public static final LocalDate TEST_END_TIME = LocalDate.of(2031, 1, 1);
    public static final RaceCategory TEST_CATEGORY = RaceCategory.TIME;

    @Autowired
    private RaceRepository raceRepository;

    @DisplayName("Race 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveRace() {
        final Race race = Race.builder()
            .category(TEST_CATEGORY)
            .title(TEST_TITLE)
            .description(TEST_DESCRIPTION)
            .certificationExample(new ImageUrl(TEST_IMAGE_URL))
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .thumbnail(new ImageUrl(TEST_IMAGE_URL))
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();

        final Race persist = raceRepository.save(race);

        assertAll(
            () -> assertThat(race.equals(persist)).isFalse(),
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getTitle()).isEqualTo(TEST_TITLE),
            () -> assertThat(persist.getDescription()).isEqualTo(TEST_DESCRIPTION),
            () -> assertThat(persist.getCertificationExample()).isEqualTo(new ImageUrl(TEST_IMAGE_URL)),
            () -> assertThat(persist.getEntranceFee()).isEqualTo(new Cash(TEST_MONEY_AMOUNT)),
            () -> assertThat(persist.getThumbnail()).isEqualTo(new ImageUrl(TEST_IMAGE_URL)),
            () -> assertThat(persist.getRaceDuration()).isEqualTo(new DateDuration(TEST_START_TIME, TEST_END_TIME)),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }
}
