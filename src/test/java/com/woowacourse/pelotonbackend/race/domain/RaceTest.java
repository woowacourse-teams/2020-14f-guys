package com.woowacourse.pelotonbackend.race.domain;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.pelotonbackend.vo.Cash;

class RaceTest {

    @DisplayName("Race가 생성될 때, 이미지에 대한 정보를 인자로 제공하지 않아도 랜덤으로 자동 생성됩니다.")
    @Test
    void staticFactoryMethodTest() {
        final Race race = Race.of(
            TEST_TITLE,
            TEST_DESCRIPTION,
            new DateDuration(TEST_START_TIME, TEST_END_TIME),
            TEST_CATEGORY,
            new Cash(TEST_MONEY_AMOUNT)
        );

        assertAll(
            () -> assertThat(race.getCertificationExample()).isNotNull(),
            () -> assertThat(race.getCertificationExample().getBaseImageUrl()).isNotNull(),
            () -> assertThat(race.getThumbnail()).isNotNull(),
            () -> assertThat(race.getThumbnail().getBaseImageUrl()).isNotNull()
        );
    }
}