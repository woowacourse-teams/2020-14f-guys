package com.woowacourse.pelotonbackend.race.presentation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;

class RaceUpdateRequestTest {
    @DisplayName("레이스 업데이트 body의 toEntity 메서드가 수정된 Race를 반환하는지 테스트합니다.")
    @Test
    void toEntity() {
        final Race race = RaceFixture.createWithId(1L);
        final RaceUpdateRequest updateRequest = RaceFixture.updateRequest();

        assertThat(updateRequest.toEntity(race)).isEqualToComparingFieldByField(RaceFixture.createUpdatedRace());
    }
}