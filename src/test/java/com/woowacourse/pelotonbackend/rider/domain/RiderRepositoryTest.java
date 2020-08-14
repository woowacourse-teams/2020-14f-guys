package com.woowacourse.pelotonbackend.rider.domain;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;

@SpringBootTest
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class RiderRepositoryTest {
    @Autowired
    private RiderRepository riderRepository;

    @DisplayName("Rider 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveRider() {
        final Rider rider = Rider.builder()
            .memberId(AggregateReference.to(1L))
            .raceId(AggregateReference.to(1L))
            .build();

        final Rider persist = riderRepository.save(rider);

        assertAll(
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getMemberId()).isEqualTo(AggregateReference.to(1L)),
            () -> assertThat(persist.getRaceId()).isEqualTo(AggregateReference.to(1L)),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }

    @DisplayName("레이스에 소속된 모든 라이더를 찾는다.")
    @Test
    void findAllByRaceId() {
        final Rider riderWithoutId = createRiderWithoutId();
        final List<Rider> expectedRiders = Arrays.asList(
            riderWithoutId,
            riderWithoutId,
            riderWithoutId);
        riderRepository.saveAll(expectedRiders);

        final List<Rider> riders = riderRepository.findRidersByRaceId(TEST_RACE_ID);

        assertThat(riders.size()).isEqualTo(expectedRiders.size());
        riders.forEach(
            rider -> assertThat(rider).isEqualToIgnoringGivenFields(riderWithoutId, "id", "createdAt", "updatedAt"));
    }

    @DisplayName("멤버가 포함된 모든 라이더를 찾는다.")
    @Test
    void findAllByMemberId() {
        final Rider riderWithoutId = createRiderWithoutId();
        final List<Rider> expectedRiders = Arrays.asList(
            riderWithoutId,
            riderWithoutId,
            riderWithoutId);
        riderRepository.saveAll(expectedRiders);

        final List<Rider> riders = riderRepository.findRidersByMemberId(TEST_MEMBER_ID);

        assertThat(riders.size()).isEqualTo(expectedRiders.size());
        riders.forEach(
            rider -> assertThat(rider).isEqualToIgnoringGivenFields(riderWithoutId, "id", "createdAt", "updatedAt"));
    }

    @DisplayName("멤버 id와 race id로 라이더를 조회한다.")
    @Test
    void existsByMemberIdAndRaceId() {
        final Rider rider = createRiderBy(1L, 1L, null);

        riderRepository.save(rider);
        assertThat(riderRepository.existsByMemberIdAndRaceID(1L, 1L)).isTrue();

        riderRepository.save(rider);
        assertThatThrownBy(() -> riderRepository.existsByMemberIdAndRaceID(1L, 1L))
            .isInstanceOf(AssertionError.class);
    }
}
