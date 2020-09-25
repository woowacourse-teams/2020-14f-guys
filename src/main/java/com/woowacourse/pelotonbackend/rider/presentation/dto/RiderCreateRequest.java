package com.woowacourse.pelotonbackend.rider.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotNull;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties("race_id")})
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RiderCreateRequest {
    @NotNull
    private final Long raceId;

    public Rider toRider(final MemberResponse memberResponse) {
        return Rider.builder()
            .memberId(AggregateReference.to(memberResponse.getId()))
            .raceId(AggregateReference.to(this.raceId))
            .build();
    }
}
