package com.woowacourse.pelotonbackend.rider.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderUpdateRequest;

public class RiderFixture {
    public static final Long TEST_RIDER_ID = 1L;
    public static final Long TEST_RIDER_ID2 = 2L;
    public static final Long WRONG_RIDER_ID = 6L;
    public static final Long TEST_RACE_ID = 1L;
    public static final Long TEST_MEMBER_ID = 1L;
    public static final Long TEST_CHANGED_RACE_ID = 8L;
    public static final Long TEST_CHANGED_MEMBER_ID = 11L;
    public static final LocalDateTime TEST_CREATED_DATE_TIME =
        LocalDateTime.parse(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    public static final int RIDER_NUMBER = 3;

    public static RiderCreateRequest createMockRequest() {
        return new RiderCreateRequest(TEST_RACE_ID);
    }

    public static RiderUpdateRequest updateMockRequest() {
        return new RiderUpdateRequest(TEST_CHANGED_RACE_ID, TEST_CHANGED_MEMBER_ID);
    }

    public static Rider createMockRider() {
        return Rider.builder().build();
    }

    public static Rider createRiderBy(final Long memberId, final Long raceId, final Long riderId) {
        return Rider.builder()
            .id(riderId)
            .memberId(AggregateReference.to(memberId))
            .raceId(AggregateReference.to(raceId))
            .build();
    }

    public static List<Rider> createRidersBy(final Long memberId) {
        return LongStream.range(1, 5)
            .mapToObj(it -> createRiderBy(memberId, it, it))
            .collect(Collectors.toList());
    }

    public static RiderResponse createRiderResponse(final Long id) {
        return RiderResponse.builder()
            .id(id)
            .memberId(TEST_MEMBER_ID)
            .raceId(TEST_RACE_ID)
            .createdAt(TEST_CREATED_DATE_TIME)
            .build();
    }

    public static Rider createRiderWithId(final Long id) {
        return Rider.builder()
            .id(id)
            .raceId(AggregateReference.to(TEST_RACE_ID))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .createdAt(TEST_CREATED_DATE_TIME)
            .build();
    }

    public static Rider createRiderWithIdAndMemberId(final Long id) {
        return Rider.builder()
            .id(id)
            .raceId(AggregateReference.to(TEST_RACE_ID))
            .memberId(AggregateReference.to(id))
            .createdAt(TEST_CREATED_DATE_TIME)
            .build();
    }

    public static Rider createRiderWithIdAndRaceId(final Long riderId, final Long raceId) {
        return Rider.builder()
            .id(riderId)
            .raceId(AggregateReference.to(raceId))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .createdAt(TEST_CREATED_DATE_TIME)
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
        return LongStream.range(1, 4)
            .mapToObj(RiderFixture::createRiderWithId)
            .collect(Collectors.collectingAndThen(Collectors.toList(), RiderResponses::of));
    }

    public static List<Rider> createRidersByCount(final int count) {
        return LongStream.range(1, count + 1)
            .mapToObj(id -> createRiderBy(id, TEST_RACE_ID, id))
            .collect(Collectors.toList());
    }

    public static List<RiderResponse> createRiderResponseByCount(final int count) {
        return RiderResponses.of(createRidersByCount(count)).getRiderResponses();
    }
}
