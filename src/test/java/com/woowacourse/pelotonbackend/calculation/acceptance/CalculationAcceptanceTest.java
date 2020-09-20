package com.woowacourse.pelotonbackend.calculation.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.calculation.domain.CalculationFixture;
import com.woowacourse.pelotonbackend.calculation.presentation.CalculationResponses;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationRequest;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionCreateRequest;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;

class CalculationAcceptanceTest extends AcceptanceTest {
    private JwtTokenResponse representToken;
    private JwtTokenResponse othersToken;
    private JwtTokenResponse thirdToken;
    private long raceId;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        final List<MemberCreateRequest> memberCreateRequests = MemberFixture.createRequests();
        final RaceCreateRequest raceCreateRequest = RaceFixture.createFinishedRequest();
        final RiderCreateRequest riderCreateRequest = RiderFixture.createMockRequest();

        final List<JwtTokenResponse> tokenResponses = loginMembers(memberCreateRequests);
        representToken = tokenResponses.get(0);
        othersToken = tokenResponses.get(1);
        thirdToken = tokenResponses.get(2);

        raceId = createRace(raceCreateRequest, representToken);
        final MissionCreateRequest missionCreateRequest = MissionFixture.mockCreateRequestByRaceId(raceId);
        createRiders(tokenResponses, riderCreateRequest);

        createMission(representToken, missionCreateRequest);
        createMission(representToken, missionCreateRequest);
        createMission(representToken, missionCreateRequest);
        createMission(othersToken, missionCreateRequest);
        final List<CertificationRequest> certificationCreateRequests =
            CertificationFixture.createMockCertificationRequestByRiderIdAndCount(
                CertificationFixture.createRiderToCount());

        createCertifications(representToken, certificationCreateRequests);
    }

    /**
     * Feature: Calculation 관리
     * <p>
     * Scenario: Calculation를 관리한다.
     * <p>
     * Given: 정산할 Race의 raceId, 정산받을 Rider의 riderId, 로그인한 Member의 tokenResponse가 주어진다.
     * When: 정산 요청(Calculation 생성 요청)을 보낸다.
     * Then: 새로운 Calculation이 생성된다.
     * <p>
     * When: 생성한 Calculation을 찾는 요청을 보낸다.
     * Then: 해당 Calculation가 조회된다.
     */


    @Test
    void manageCalculation() {
        createCalculation(representToken, raceId);
        retrieveCalculation(representToken, raceId);
    }

    @DisplayName("캐시를 적용한 정산 조회의 첫번째 요청이 이후 요청보다 경과시간이 적다")
    @Test
    void cachingCalculationRetrieving() {
        createCalculation(representToken, raceId);

        final long firstElapsedTime = getElapsedTime(() -> retrieveCalculationResponses(representToken, raceId));
        final long secondElapsedTime = getElapsedTime(() -> retrieveCalculationResponses(representToken, raceId));
        final long thirdElapsedTime = getElapsedTime(() -> retrieveCalculationResponses(representToken, raceId));
        final long fourthElapsedTime = getElapsedTime(() -> retrieveCalculationResponses(representToken, raceId));

        assertAll(
            () -> assertThat(firstElapsedTime).isGreaterThan(secondElapsedTime),
            () -> assertThat(firstElapsedTime).isGreaterThan(thirdElapsedTime),
            () -> assertThat(firstElapsedTime).isGreaterThan(fourthElapsedTime)
        );
    }

    private void retrieveCalculation(final JwtTokenResponse tokenResponse, final long raceId) {
        final CalculationResponses responses = retrieveCalculationResponses(tokenResponse, raceId);

        assertThat(responses.getCalculationResponses())
            .usingRecursiveFieldByFieldElementComparator()
            .usingElementComparatorIgnoringFields("createdAt")
            .containsExactlyInAnyOrderElementsOf(CalculationFixture.createAcceptanceResponses());
    }

    private CalculationResponses retrieveCalculationResponses(JwtTokenResponse tokenResponse, long raceId) {
        final CalculationResponses responses = given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/api/calculations/races/{raceId}", raceId)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().as(CalculationResponses.class);
        return responses;
    }

    private void createCalculation(final JwtTokenResponse tokenResponse, final long raceId) {
        given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/calculations/races/{raceId}", raceId)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", String.format("/api/calculations/races/%d", raceId));
    }
}
