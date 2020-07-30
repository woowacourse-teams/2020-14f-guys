package com.woowacourse.pelotonbackend.rider.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.rider.presentation.RiderResponse;

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

    @DisplayName("아이디로 Rider를 조회한다.")
    @Test
    void findById() {
        final Rider expectedRider = RiderFixture.createRiderWithId(RiderFixture.TEST_RIDER_ID);
        when(riderRepository.findById(anyLong())).thenReturn(Optional.of(expectedRider));

        final RiderResponse retrieveRider = riderService.retrieve(1L);

        assertAll(
            () -> assertThat(retrieveRider.getMemberId()).isEqualTo(expectedRider.getMemberId().getId()),
            () -> assertThat(retrieveRider.getRaceId()).isEqualTo(expectedRider.getRaceId().getId()),
            () -> assertThat(retrieveRider).isEqualToIgnoringGivenFields(expectedRider, "raceId","memberId")
        );
    }
}
