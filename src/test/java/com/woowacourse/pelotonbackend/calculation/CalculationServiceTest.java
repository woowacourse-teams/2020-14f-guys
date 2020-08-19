package com.woowacourse.pelotonbackend.calculation;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.query.application.QueryService;
import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.vo.Cash;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {
    @Mock
    private CalculationRepository calculationRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private RiderService riderService;
    @Mock
    private QueryService queryService;
    @Mock
    private RaceService raceService;

    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService(calculationRepository, memberService, riderService, queryService,
            raceService);
    }

    @Test
    void calculation() {
        // when(calculationRepository.findAllByRaceId(anyLong())).thenReturn(Lists.emptyList());
        // when(riderService.retrieveByRaceId(anyLong())).thenReturn(RiderFixture.createRidersInSameRaceByCount(5));
        // when(queryService.findCertificationsByRaceId(anyLong(), any())).thenReturn(
        //     CertificationFixture.createMockRaceCertifications());
        // when(memberService.findAllById(anyList())).thenReturn(
        //     MemberResponses.from(Lists.newArrayList(MemberFixture.createWithId(1L, "지건"), MemberFixture.createWithId(2L, "텟카이"),
        //         MemberFixture.createWithId(3L, "람각"), MemberFixture.createWithId(4L, "오예"),
        //         MemberFixture.createWithId(5L, "범"))));
        // when(raceService.retrieve(anyLong())).thenReturn(
        //     RaceResponse.builder().entranceFee(new Cash(BigDecimal.valueOf(10000))).build());
        //
        // final CalculationResponses response = calculationService.calculate(null, 1L, 1L);
    }
}