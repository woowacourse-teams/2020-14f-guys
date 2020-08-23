package com.woowacourse.pelotonbackend.calculation.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.calculation.domain.CalculationFixture;
import com.woowacourse.pelotonbackend.calculation.domain.CalculationRepository;
import com.woowacourse.pelotonbackend.calculation.presentation.CalculationResponse;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.common.exception.CalculationNotFoundException;
import com.woowacourse.pelotonbackend.common.exception.DuplicateCalculationException;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFinishedException;
import com.woowacourse.pelotonbackend.common.exception.UnAuthenticatedException;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.query.application.QueryService;
import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
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

    private final Map<Long, Integer> countToRiderId = new LinkedHashMap<>();
    private MemberResponse memberResponse;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService(calculationRepository, memberService, riderService, queryService,
            raceService);
        countToRiderId.put(1L, 5);
        countToRiderId.put(2L, 4);
        countToRiderId.put(3L, 3);
        countToRiderId.put(4L, 2);
        countToRiderId.put(5L, 0);
        memberResponse = MemberFixture.memberResponse();
    }

    @DisplayName("정상적으로 Calculation을 생성한다.")
    @Test
    void create() {
        when(riderService.retrieveByRaceId(anyLong())).thenReturn(
            new RiderResponses(RiderFixture.createRidersInSameRaceByCount(5)));
        when(queryService.findCertificationsByRaceId(anyLong(), any())).thenReturn(
            CertificationFixture.createMockRaceCertifications(countToRiderId));
        when(raceService.retrieve(anyLong())).thenReturn(RaceFixture.retrieveFinishedResponse());

        calculationService.calculate(MemberFixture.memberResponse(), RaceFixture.TEST_RACE_ID);

        verify(memberService).chargeCash(anyLong(), any());
        verify(calculationRepository).saveAll(any());
    }

    @DisplayName("정상적으로 Calculation을 가져온다.")
    @Test
    void retrieve() {
        when(riderService.retrieveByRaceId(anyLong())).thenReturn(
            new RiderResponses(RiderFixture.createRidersInSameRaceByCount(5)));
        when(raceService.retrieve(anyLong())).thenReturn(RaceFixture.retrieveFinishedResponse());
        when(calculationRepository.findAllByRaceId(anyLong())).thenReturn(
            Optional.of(CalculationFixture.createCalculations(5, RiderFixture.TEST_RIDER_ID)));

        final List<CalculationResponse> results = calculationService.retrieve(
            memberResponse, RaceFixture.TEST_RACE_ID).getCalculationResponses();

        assertAll(
            () -> assertThat(results).extracting(CalculationResponse::getRaceId)
                .allMatch(Predicate.isEqual(RaceFixture.TEST_RACE_ID)),
            () -> assertThat(results).extracting(CalculationResponse::getRiderId)
                .containsExactlyElementsOf(countToRiderId.keySet()),
            () -> assertThat(results).allMatch(
                calculationResponse -> calculationResponse.isCalculated() == calculationResponse.getRiderId()
                    .equals(RiderFixture.TEST_RIDER_ID)),
            () -> assertThat(results).extracting(CalculationResponse::getPrize).containsExactly(
                Cash.of(10000),
                Cash.of(10000),
                Cash.of(10000),
                Cash.of(10000),
                Cash.of(10000)
            ));
    }

    @DisplayName("회원의 아이디가 비정상적인 값이 입력되었을 때")
    @Test
    void invalidMemberRetrieve() {
        when(riderService.retrieveByRaceId(anyLong())).thenReturn(
            new RiderResponses(RiderFixture.createRidersInSameRaceByCount(5)));
        when(raceService.retrieve(anyLong())).thenReturn(RaceFixture.retrieveResponse());

        final MemberResponse memberResponse = MemberFixture.memberResponse(MemberFixture.WRONG_MEMBER_ID);
        assertThatThrownBy(
            () -> calculationService.retrieve(memberResponse, RaceFixture.TEST_RACE_ID))
            .isInstanceOf(UnAuthenticatedException.class)
            .hasMessage(String.format("회원 id : %d 는 권한이 없습니다.", memberResponse.getId()));
    }

    @DisplayName("레이스가 아직 종료되지 않은 경우 예외를 반환한다.")
    @Test
    void notFinishedRaceRetrieve() {
        when(riderService.retrieveByRaceId(anyLong())).thenReturn(
            new RiderResponses(RiderFixture.createRidersInSameRaceByCount(5)));
        when(raceService.retrieve(anyLong())).thenReturn(RaceFixture.retrieveNotFinishedResponse());

        assertThatThrownBy(
            () -> calculationService.retrieve(memberResponse, RaceFixture.TEST_RACE_ID))
            .isInstanceOf(RaceNotFinishedException.class)
            .hasMessage(String.format("레이스 id : %d가 아직 진행중이에요!", RaceFixture.TEST_RACE_ID));
    }

    @DisplayName("정산되지 않은 결과를 조회할 때 예외를 반환한다.")
    @Test
    void notFoundCalculation() {
        when(riderService.retrieveByRaceId(anyLong())).thenReturn(
            new RiderResponses(RiderFixture.createRidersInSameRaceByCount(5)));
        when(raceService.retrieve(anyLong())).thenReturn(RaceFixture.retrieveFinishedResponse());
        when(calculationRepository.findAllByRaceId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(
            () -> calculationService.retrieve(memberResponse, RaceFixture.TEST_RACE_ID))
            .isInstanceOf(CalculationNotFoundException.class)
            .hasMessage(String.format("Calculation(race id = %d) does not exist)", RaceFixture.TEST_RACE_ID));
    }

    @DisplayName("이미 정산된 회원이 다시 정산을 요청하면 예외를 반환한다.")
    @Test
    void duplicateCalculation() {
        when(riderService.retrieveByRaceId(anyLong())).thenReturn(
            new RiderResponses(RiderFixture.createRidersInSameRaceByCount(5)));
        when(raceService.retrieve(anyLong())).thenReturn(RaceFixture.retrieveFinishedResponse());
        when(calculationRepository.findAllByRaceId(anyLong())).thenReturn(Optional.of(CalculationFixture.createCalculations(3,RiderFixture.TEST_RIDER_ID)));

        assertThatThrownBy(() -> calculationService.calculate(MemberFixture.memberResponse(), RaceFixture.TEST_RACE_ID))
            .isInstanceOf(DuplicateCalculationException.class);
    }
}
