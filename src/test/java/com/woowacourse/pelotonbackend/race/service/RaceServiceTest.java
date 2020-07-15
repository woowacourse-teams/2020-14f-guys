package com.woowacourse.pelotonbackend.race.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;

@ExtendWith(MockitoExtension.class)
class RaceServiceTest {
    @Mock
    RaceRepository raceRepository;

    RaceService raceService;

    @BeforeEach
    void setUp() {
        raceService = new RaceService(raceRepository);
    }

    @DisplayName("정상적으로 save 메서드가 호출되는지 테스트합니다.")
    @Test
    void save() {
        raceService.save(createMockRace());
        verify(raceRepository).save(any());
    }

    Race createMockRace() {
        return Race.builder().build();
    }
}