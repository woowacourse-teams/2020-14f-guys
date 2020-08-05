package com.woowacourse.pelotonbackend.rider.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;

@ExtendWith(MockitoExtension.class)
public class RiderServiceTest {
    @Mock
    private RiderRepository riderRepository;

    private RiderService riderService;

    @BeforeEach
    void setUp() {
        riderService = new RiderService(riderRepository);
    }

    @DisplayName("create시 save가 정상적으로 완료되는지 확인")
    @Test
    void createTest() {
        given(riderRepository.save(any())).willReturn(RiderFixture.createMockRider());
        riderService.create(MemberFixture.memberResponse(), RiderFixture.createMockRequest());
        verify(riderRepository).save(any());
    }

    @DisplayName("race id로 Rider를 조회한다.")
    @Test
    void findByRaceId() {
        final Rider expectedRider = RiderFixture.createRiderWithId(RiderFixture.TEST_RIDER_ID);
        given(riderRepository.findById(RiderFixture.TEST_RIDER_ID)).willReturn(Optional.of(expectedRider));

        final RiderResponse retrieveRider = riderService.retrieve(RaceFixture.TEST_RACE_ID);

        assertThat(retrieveRider.getId()).isEqualTo(expectedRider.getId());
        assertThat(retrieveRider.getRaceId()).isEqualTo(expectedRider.getRaceId().getId());
        assertThat(retrieveRider.getMemberId()).isEqualTo(expectedRider.getMemberId().getId());
    }

    @DisplayName("member id로 Rider를 조회한다.")
    @Test
    void findByMemberId() {
        final Rider expectedRider = RiderFixture.createRiderWithId(RiderFixture.TEST_RIDER_ID);
        given(riderRepository.findById(RiderFixture.TEST_RIDER_ID)).willReturn(Optional.of(expectedRider));

        final RiderResponse retrieveRider = riderService.retrieve(RiderFixture.TEST_MEMBER_ID);

        assertThat(retrieveRider.getId()).isEqualTo(expectedRider.getId());
        assertThat(retrieveRider.getRaceId()).isEqualTo(expectedRider.getRaceId().getId());
        assertThat(retrieveRider.getMemberId()).isEqualTo(expectedRider.getMemberId().getId());
    }

    @DisplayName("Rider를 업데이트한다.")
    @Test
    void update() {
        final long riderId = RiderFixture.TEST_RIDER_ID;
        final Rider expectedRider = RiderFixture.updateRiderWithId(riderId);
        given(riderRepository.findById(riderId)).willReturn(Optional.of(RiderFixture.createRiderWithId(riderId)));
        given(riderRepository.save(expectedRider)).willReturn(expectedRider);

        final Long updatedRiderId = riderService.updateById(riderId, RiderFixture.updateMockRequest());

        assertThat(updatedRiderId).isEqualTo(expectedRider.getId());
    }

    @DisplayName("Rider를 삭제한다.")
    @Test
    void delete() {
        riderService.deleteById(RiderFixture.TEST_RIDER_ID);

        verify(riderRepository).deleteById(RiderFixture.TEST_RIDER_ID);
    }
}
