package com.woowacourse.pelotonbackend.race.presentation.dto;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceUpdateRequest;

class RaceUpdateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("RaceUpdateRequest가 올바르게 Deserialize되는 지 확인한다.")
    @Test
    void raceUpdateRequestTest() throws JsonProcessingException {
        final String requestBody = "{\n"
            + "\"title\":\"14층 녀석들 지각 안하기 레이스\",\n"
            + "\"description\":\"10시 데일리에 늦지 않고 참가해보자!\",\n"
            + "\"race_duration\":{\n"
            + "\"start_date\":\""+TEST_CHANGED_START_TIME+"\",\n"
            + "\"end_date\":\""+TEST_CHANGED_END_TIME+"\"\n"
            + "},\n"
            + "\"category\":\"STUDY\",\n"
            + "\"entrance_fee\":\"25000\",\n"
            + "\"certification\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6V\",\n"
            + "\"thumbnail\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6V\"\n"
            + "}";

        assertThat(objectMapper.readValue(requestBody, RaceUpdateRequest.class)).isEqualToComparingFieldByField(updateRequest());
    }

    @DisplayName("레이스 업데이트 body의 toEntity 메서드가 수정된 Race를 반환하는지 테스트합니다.")
    @Test
    void toEntity() {
        final Race race = RaceFixture.createWithId(1L);
        final RaceUpdateRequest updateRequest = RaceFixture.updateRequest();

        assertThat(updateRequest.toRace(race)).isEqualToComparingFieldByField(RaceFixture.createUpdatedRace());
    }
}
