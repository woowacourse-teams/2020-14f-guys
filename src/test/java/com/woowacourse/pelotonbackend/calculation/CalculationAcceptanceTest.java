package com.woowacourse.pelotonbackend.calculation;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
    /**
     * Feature: Calculation 관리
     *
     * Scenario: Calculation를 관리한다.
     *
     * Given: 정산할 Race의 raceId, 정산받을 Rider의 riderId, 로그인한 Member의 tokenResponse가 주어진다.
     * When: 정산 요청(Calculation 생성 요청)을 보낸다.
     * Then: 새로운 Calculation이 생성된다.
     *
     * When: 생성한 Calculation을 찾는 요청을 보낸다.
     * Then: 해당 Calculation가 조회된다.
     */
    @Test
    void manageCalculation() {
        final List<MemberCreateRequest> memberCreateRequests = MemberFixture.createRequests();
        final RaceCreateRequest raceCreateRequest = RaceFixture.createFinishedRequest();
        final RiderCreateRequest riderCreateRequest = RiderFixture.createMockRequest();

        final List<JwtTokenResponse> tokenResponses = loginMembers(memberCreateRequests);
        final JwtTokenResponse representToken = tokenResponses.get(0);

        final long raceId = createRace(raceCreateRequest, representToken);
        final MissionCreateRequest missionCreateRequest = MissionFixture.mockCreateRequestByRaceId(raceId);
        createRiders(tokenResponses, riderCreateRequest);

        createMission(representToken, missionCreateRequest);
        createMission(representToken, missionCreateRequest);
        createMission(representToken, missionCreateRequest);
        final List<CertificationRequest> certificationCreateRequests =
            CertificationFixture.createMockCertificationRequestByRiderIdAndCount(
                CertificationFixture.createRiderToCount());

        createCertifications(representToken, certificationCreateRequests);

        createCalculation(representToken, raceId, 1L);
        retrieveCalculation(representToken, raceId, 1L);
    }

    private void retrieveCalculation(final JwtTokenResponse tokenResponse, final long raceId, final long riderId) {
        final CalculationResponses responses = given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/api/calculations/races/{raceId}/riders/{riderId}", raceId, riderId)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().as(CalculationResponses.class);

        assertThat(responses.getCalculationResponses())
            .usingRecursiveFieldByFieldElementComparator()
            .usingElementComparatorIgnoringFields("createdAt")
            .isEqualTo(CalculationFixture.createAcceptanceResponses());
    }

    private void createCalculation(final JwtTokenResponse tokenResponse, final long raceId, final long riderId) {
        given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/calculations/races/{raceId}/riders/{riderId}", raceId, riderId)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", String.format("/api/calculations/races/%d/riders/%d", raceId, riderId));
    }

    private void createMission(final JwtTokenResponse tokenResponse, final MissionCreateRequest request) {
        given()
            .header(createTokenHeader(tokenResponse))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/api/missions")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
