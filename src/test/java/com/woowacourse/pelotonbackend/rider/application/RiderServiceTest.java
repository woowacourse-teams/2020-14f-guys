package com.woowacourse.pelotonbackend.rider.application;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;

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
        riderService.create(RiderFixture.createMockRequest());
        verify(riderRepository).save(any());
    }
}
