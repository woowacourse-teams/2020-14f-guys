package com.woowacourse.pelotonbackend.mission.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface MissionRepositoryCustom {
    List<Mission> findByRaceId(Long raceId);

    List<Mission> findAllByRaceIdsEndTimeAfterThanAndWithinOneDayOrderByStartTime(List<Long> raceIds, final LocalDateTime criterion);
}
