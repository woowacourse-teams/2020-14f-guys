package com.woowacourse.pelotonbackend.query;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationResponse;

@ExtendWith(SpringExtension.class)
class QueryServiceTest {
    private QueryService queryService;

    @Mock
    private QueryRepositoryCustom queryRepositoryCustom;

    @BeforeEach
    void setUp() {
        queryService = new QueryService(queryRepositoryCustom);
    }

    @DisplayName("레이스의 대표적인 사진을 조회할 수 있다.")
    @Test
    void findCertificationsByRaceId() {
        final PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.DESC, "id");
        given(queryRepositoryCustom.findCertificationsByRaceId(any(), any()))
            .willReturn(CertificationFixture.createMockPagedCertifications(pageRequest));
        final Page<CertificationResponse> response = queryService.findCertificationsByRaceId(TEST_RACE_ID,
            pageRequest).getCertifications();

        assertAll(
            () -> assertThat(response.getContent().size()).isEqualTo(1),
            () -> assertThat(response.getPageable().getPageNumber()).isEqualTo(0),
            () -> assertThat(response.getTotalPages()).isEqualTo(4),
            () -> assertThat(response.getTotalElements()).isEqualTo(4)
        );
    }
}