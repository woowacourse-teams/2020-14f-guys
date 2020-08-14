package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.beans.ConstructorProperties;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties("upcomingMissions"))
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpcomingMissionResponses {
    private final List<UpcomingMissionResponse> upcomingMissions;
}
