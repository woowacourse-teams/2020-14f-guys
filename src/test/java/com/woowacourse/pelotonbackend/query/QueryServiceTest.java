package com.woowacourse.pelotonbackend.query;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {

    private QueryService queryService;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private CertificationRepository certificationRepository;

    @BeforeEach
    void setUp() {
        queryService = new QueryService(missionRepository, certificationRepository);
    }

    @DisplayName("레이스 아이디로 인증사진을 조회한다."
        + "페이지 정보 : 총 게시물 4개, 첫번째 페이지, 페이지당 컨텐츠 1개")
    @Test
    void findCertificationByRaceId() {
        final PageRequest page = PageRequest.of(0, 1);
        when(missionRepository.findByRaceId(anyLong())).thenReturn(Arrays.asList(MissionFixture.createWithId()));
        when(certificationRepository.findByMissionIds(any(), any(Pageable.class)))
            .thenReturn(CertificationFixture.createMockPagedCertifications(page));
        final Page<CertificationResponse> certifications = queryService.findCertificationsByRaceId(1L, page)
            .getCertifications();

        assertAll(
            () -> assertThat(certifications.getContent()).hasSize(1),
            () -> assertThat(certifications.getTotalPages()).isEqualTo(4),
            () -> assertThat(certifications.getPageable().getPageNumber()).isEqualTo(0)
        );

    }
}