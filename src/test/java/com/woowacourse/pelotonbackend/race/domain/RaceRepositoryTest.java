package com.woowacourse.pelotonbackend.race.domain;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

@SpringBootTest
public class RaceRepositoryTest {
    @Autowired
    private RaceRepository raceRepository;

    @DisplayName("Race 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveRace() {
        final Race race = RaceFixture.createWithoutId();
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
            () -> assertThat(persist.getCategory()).isNotNull(),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }
}
