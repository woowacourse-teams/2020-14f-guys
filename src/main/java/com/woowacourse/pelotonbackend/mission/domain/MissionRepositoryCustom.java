package com.woowacourse.pelotonbackend.mission.domain;

import java.util.List;

public interface MissionRepositoryCustom {
    List<Mission> findByRaceId(Long raceId);
}
