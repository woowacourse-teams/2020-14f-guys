package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.beans.ConstructorProperties;
import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionResponse;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE,
    onConstructor_ = @ConstructorProperties("rider, race, mission, certification"))
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpcomingMissionResponse {
    private final RiderResponse rider;
    private final RaceResponse race;
    private final MissionResponse mission;
    @Nullable
    private final CertificationResponse certification;

    public static UpcomingMissionResponse of(final Mission mission, final Rider rider,
        final Race race, @Nullable final Certification certification) {

        CertificationResponse certificationResponse;
        if (Objects.isNull(certification)) {
            certificationResponse = null;
        } else {
            certificationResponse = CertificationResponse.of(certification);
        }

        return new UpcomingMissionResponse(RiderResponse.of(rider), RaceResponse.of(race),
            MissionResponse.of(mission), certificationResponse);
    }
}
