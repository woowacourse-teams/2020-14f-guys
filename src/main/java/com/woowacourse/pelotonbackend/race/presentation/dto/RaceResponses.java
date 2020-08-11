package com.woowacourse.pelotonbackend.race.presentation.dto;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.stream.Collectors;

import com.woowacourse.pelotonbackend.race.domain.Race;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("raceResponses"))
@Getter
public class RaceResponses {
    private final List<RaceResponse> raceResponses;

    public static RaceResponses of(final List<Race> races) {
        return races.stream()
            .map(RaceResponse::of)
            .collect(Collectors.collectingAndThen(Collectors.toList(), RaceResponses::new));
    }
}
