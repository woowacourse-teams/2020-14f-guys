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
class RaceRepositoryTest {
    @Autowired
    private RaceRepository raceRepository;

    @DisplayName("Race 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveRace() {
        final Race race = Race.builder()
            .category(RaceCategory.TIME)
            .title("14층 녀석들 기상 레이스")
            .description("아침 6시에 일어나보자!")
            .certificationExample(new ImageUrl("https://abc.com"))
            .entranceFee(new Cash(new BigDecimal(20000)))
            .thumbnail(new ImageUrl("https://def.com"))
            .raceDuration(new DateDuration(LocalDate.of(2030, 1, 1), LocalDate.of(2031, 1, 1)))
            .build();

        final Race persist = raceRepository.save(race);

        assertAll(
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getTitle()).isEqualTo("14층 녀석들 기상 레이스"),
            () -> assertThat(persist.getDescription()).isEqualTo("아침 6시에 일어나보자!"),
            () -> assertThat(persist.getCertificationExample()).isEqualTo(new ImageUrl("https://abc.com")),
            () -> assertThat(persist.getEntranceFee()).isEqualTo(new Cash(new BigDecimal(20000))),
            () -> assertThat(persist.getThumbnail()).isEqualTo(new ImageUrl("https://def.com")),
            () -> assertThat(persist.getRaceDuration()).isEqualTo(
                new DateDuration(LocalDate.of(2030, 1, 1), LocalDate.of(2031, 1, 1))),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }
}
