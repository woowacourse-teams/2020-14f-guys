package com.woowacourse.pelotonbackend.rider.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.rider.presentation.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;

public class RiderFixture {
    public static Long TEST_RIDER_ID = 1L;
    public static Long TEST_RACE_ID = 1L;
    public static Long TEST_MEMBER_ID = 1L;
    public static final int RIDER_NUMBER = 4;

    public static RiderCreateRequest createMockRequest() {
        return new RiderCreateRequest(TEST_RACE_ID);
    }

    public static Rider createMockRider() {
        return Rider.builder().build();
    }

    public static RiderResponse createRiderResponse(final Long id) {
        return RiderResponse.builder()
            .id(id)
            .memberId(TEST_MEMBER_ID)
            .raceId(TEST_RACE_ID)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Rider createRiderWithId(final Long id) {
        return Rider.builder()
            .id(id)
            .raceId(AggregateReference.to(TEST_RACE_ID))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .build();
    }

    public static Rider createRiderWithoutId() {
        return Rider.builder()
            .raceId(AggregateReference.to(TEST_RACE_ID))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .build();
    }

    public static List<RiderResponse> createRidersInSameRace() {
        return LongStream.range(1, 5)
            .mapToObj(RiderFixture::createRiderResponse)
            .collect(Collectors.toList());
    }
}
