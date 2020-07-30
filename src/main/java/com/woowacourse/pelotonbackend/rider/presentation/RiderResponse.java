package com.woowacourse.pelotonbackend.rider.presentation;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = {@ConstructorProperties({"id", "memberId", "raceId", "createdAt"})})
@Builder
@Getter
public class RiderResponse {
    private final Long id;
    private final Long memberId;
    private final Long raceId;
    private final LocalDateTime createdAt;

    public static RiderResponse of(final Rider rider) {
        return RiderResponse.builder()
            .id(rider.getId())
            .memberId(rider.getMemberId().getId())
            .raceId(rider.getRaceId().getId())
            .createdAt(rider.getCreatedAt())
            .build();
    }

    public static List<RiderResponse> listOf(final List<Rider> riders) {
        return riders.stream()
            .map(RiderResponse::of)
            .collect(Collectors.toList());
    }
}
