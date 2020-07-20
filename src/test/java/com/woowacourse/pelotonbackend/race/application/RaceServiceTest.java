package com.woowacourse.pelotonbackend.race.application;

import static com.woowacourse.pelotonbackend.race.domain.RaceCategory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.exception.NotExistRaceException;
import com.woowacourse.pelotonbackend.race.presentation.RaceRetrieveResponse;
import com.woowacourse.pelotonbackend.support.RandomGenerator;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {
    @Mock
    private RaceRepository raceRepository;

    @Mock
    private RandomGenerator randomGenerator;

    private RaceService raceService;

    @BeforeEach
    void setUp() {
        raceService = new RaceService(raceRepository, randomGenerator);
    }

    @DisplayName("Race 생성이 정상적으로 되는지 확인합니다.")
    @Test
    void create() {
        final Race race = RaceFixture.createWithoutId();
        final Race savedRace = RaceFixture.createMockRequest()
            .toEntity(RaceFixture.TEST_CERTIFICATION_URL, RaceFixture.TEST_THUMBNAIL_URL);
        given(raceRepository.save(race)).willReturn(savedRace);
        final RaceCategory category = race.getCategory();
        given(randomGenerator.getRandomIntLowerThan(category.getCertifications().size())).willReturn(0);
        given(randomGenerator.getRandomIntLowerThan(category.getThumbnails().size())).willReturn(0);

        final Long raceId = raceService.create(RaceFixture.createMockRequest());

        assertThat(raceId).isEqualTo(race.getId());
    }

    @DisplayName("retrieve 메서드가 race를 리턴하는지 테스트합니다.")
    @Test
    void retrieve() {
        final Race race = RaceFixture.createWithUrls(TIME.getCertifications().get(0), TIME.getThumbnails().get(0));
        given(raceRepository.findById(race.getId())).willReturn(Optional.of(race));

        final RaceRetrieveResponse responseBody = raceService.retrieve(race.getId());

        assertThat(responseBody).isEqualToComparingFieldByField(RaceRetrieveResponse.of(race));
    }

    @DisplayName("존재하지 않는 Race를 찾을 때, 예외를 던지는지 테스트합니다.")
    @Test
    void retrieveNotExist() {
        given(raceRepository.findById(anyLong())).willReturn(Optional.empty());

        final long notExistRaceId = 100L;
        assertThatThrownBy(() -> raceService.retrieve(notExistRaceId))
            .isInstanceOf(NotExistRaceException.class)
            .hasMessage(String.format("Race(id: %d) is not exists", notExistRaceId));
    }
}
