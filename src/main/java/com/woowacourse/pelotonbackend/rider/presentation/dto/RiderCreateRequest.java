package com.woowacourse.pelotonbackend.rider.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotNull;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"member_id", "race_id"}))
@Getter
public class RiderCreateRequest {
    @NotNull
    private final Long memberId;

    @NotNull
    private final Long raceId;

    public Rider toRider() {
        return Rider.builder()
            .memberId(AggregateReference.to(this.memberId))
            .raceId(AggregateReference.to(this.raceId))
            .build();
    }
}
