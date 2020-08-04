package com.woowacourse.pelotonbackend.rider.domain;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderUpdateRequest;

public class RiderFixture {
    public static final Long TEST_RIDER_ID = 1L;
    public static final Long TEST_RACE_ID = 7L;
    public static final Long TEST_MEMBER_ID = 1L;
    public static final Long TEST_CHANGED_RACE_ID = 8L;
    public static final Long TEST_CHANGED_MEMBER_ID = 11L;
    public static final int RIDER_NUMBER = 4;

    public static RiderCreateRequest createMockRequest() {
        return new RiderCreateRequest(TEST_RACE_ID);
    }

    public static RiderUpdateRequest updateMockRequest() {
        return new RiderUpdateRequest(TEST_CHANGED_RACE_ID, TEST_CHANGED_MEMBER_ID);
    }

    public static Rider createMockRider() {
        return Rider.builder().build();
    }

    public static RiderResponse createRiderResponse(final Long id) {
        return RiderResponse.builder()
            .id(id)
            .memberId(TEST_MEMBER_ID)
            .raceId(TEST_RACE_ID)
            .build();
    }

    public static Rider createRiderWithId(final Long id) {
        return Rider.builder()
            .id(id)
            .raceId(AggregateReference.to(TEST_RACE_ID))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .build();
    }

    public static Rider updateRiderWithId(final Long id) {
        return Rider.builder()
            .id(id)
            .raceId(AggregateReference.to(TEST_CHANGED_RACE_ID))
            .memberId(AggregateReference.to(TEST_CHANGED_MEMBER_ID))
            .build();
    }

    public static Rider createRiderWithoutId() {
        return Rider.builder()
            .raceId(AggregateReference.to(TEST_RACE_ID))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .build();
    }

    public static RiderResponses createRidersInSameRace() {
        return RiderResponses.from(LongStream.range(1, 5)
            .mapToObj(RiderFixture::createRiderWithId)
            .collect(Collectors.toList()));
    }
}
