package com.woowacourse.pelotonbackend.race.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull(),
            () -> assertThat(persist).isEqualToIgnoringNullFields(race)
        );
    }
}
