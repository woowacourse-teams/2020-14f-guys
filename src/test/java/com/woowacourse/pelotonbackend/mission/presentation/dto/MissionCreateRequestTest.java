package com.woowacourse.pelotonbackend.mission.presentation.dto;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;

class MissionCreateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("요청이 정상적으로 dto로 변환되는지 확인한다.")
    @Test
    void missionCreateRequestTest() throws JsonProcessingException {
        final String requestBody = "{\n"
            + "    \"mission_duration\": {\n"
            + "        \"start_time\": " + "\"" + MissionFixture.START_TIME + "Z\",\n"
            + "        \"end_time\": " + "\"" + MissionFixture.END_TIME + "Z\"\n"
            + "    },\n"
            + "    \"mission_instruction\": " + "\"" + MissionFixture.MISSION_INSTRUCTION.getMissionInstruction() + "\"\n,"
            + "    \"race_id\": 7"
            + "}";

        final MissionCreateRequest missionCreateRequest = objectMapper.readValue(requestBody,
            MissionCreateRequest.class);

        assertThat(missionCreateRequest).isEqualToComparingFieldByField(MissionFixture.mockCreateRequest());
    }
}
