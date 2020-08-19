package com.woowacourse.pelotonbackend.certification.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;

class CalculationsTest {
    private List<RiderResponse> riders;
    private List<CertificationResponse> certifications;
    private RaceResponse race;

    @BeforeEach
    void setUp() {
        final Map<Integer, Long> countToRiderId = new HashMap<>();
        countToRiderId.put(5, 1L);
        countToRiderId.put(4, 2L);
        countToRiderId.put(3, 3L);
        countToRiderId.put(2, 4L);
        countToRiderId.put(0, 5L);
        riders = RiderFixture.createRidersInSameRaceByCount(5);
    }
}