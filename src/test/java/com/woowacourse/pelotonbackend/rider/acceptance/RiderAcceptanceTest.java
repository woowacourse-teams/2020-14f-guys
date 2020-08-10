package com.woowacourse.pelotonbackend.rider.acceptance;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderUpdateRequest;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;

public class RiderAcceptanceTest extends AcceptanceTest {
    /**
     * Feature: Rider 관리
     *
     * Scenario: Rider를 관리한다.
     * Given: Member가 생성이 되어있다.(해당 멤버의 토큰이 발급되어 있다.)
     *        Race가 생성이 되어있다.
     * When: Member가 Race에 입장을 요청한다.(Rider 생성 요청)
     * Then: 새로운 Rider가 생성된다.
     *
     * Given: Rider가 존재한다.
     * When: 라이더를 조회한다.
     * Then: 라이더를 반환한다.
     *
     * Given: Race에 참여중인 멤버들이 존재한다.
     * When: Race에 참여중인 Rider들을 RACE ID 기준으로 조회한다.
     * Then: 참여중인 Rider들이 반환된다.
     * When: Race에 참여중인 Rider들을 MEMBER ID 기준으로 조회한다.
     * Then: 참여중인 Rider들이 반환된다.
     *
     * Given: Rider를 업데이트한다.
     * When: 라이더를 조회한다.
     * Then: 업데이트된 라이더가 반환된다.
     *
     * Given: Rider를 삭제한다.
     * When: 라이더를 조회한다.
     * Then: 라이더가 조회되지 않는다.
     */
    @DisplayName("Rider 관리 기능")
    @Test
    void manageRider() {
        final RiderCreateRequest riderCreateRequest = RiderFixture.createMockRequest();
        final JwtTokenResponse tokenResponse = loginMember(
            MemberFixture.createRequest(MemberFixture.KAKAO_ID, MemberFixture.EMAIL, MemberFixture.NAME));
        final MemberResponse memberResponse = requestFind(MemberFixture.KAKAO_ID);
        final String resource = fetchCreateRider(tokenResponse, riderCreateRequest);
        final RiderResponse riderResponse = fetchFindRider(resource, tokenResponse);

        assertThat(riderResponse.getId()).isNotNull();
        assertThat(riderResponse.getMemberId()).isEqualTo(memberResponse.getId());
        assertThat(riderResponse.getRaceId()).isEqualTo(riderCreateRequest.getRaceId());

        fetchCreateRiders(tokenResponse, riderCreateRequest);
        fetchFindRidersByRaceId(TEST_RACE_ID, tokenResponse);
        fetchFindRidersByMemberId(memberResponse.getId(), tokenResponse);

        fetchUpdateRider(resource, tokenResponse);
        final RiderResponse updatedRiderResponse = fetchFindRider(resource, tokenResponse);

        assertThat(updatedRiderResponse.getMemberId()).isEqualTo((TEST_CHANGED_MEMBER_ID));
        assertThat(updatedRiderResponse.getRaceId()).isEqualTo(TEST_CHANGED_RACE_ID);

        fetchDeleteRider(resource, tokenResponse);
        fetchFindRiderFailed(resource, tokenResponse);
    }

    private void fetchCreateRiders(final JwtTokenResponse tokenResponse, final RiderCreateRequest riderCreateRequest) {
        for (int i = 0; i < RIDER_NUMBER; i++) {
            fetchCreateRider(tokenResponse, riderCreateRequest);
        }
    }

    private void fetchFindRidersByRaceId(final Long raceId, final JwtTokenResponse tokenResponse) {
        final RiderResponses riders = given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/api/riders/races/{raceId}", raceId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .as(RiderResponses.class);

        assertThat(riders.getRiderResponses().size()).isEqualTo(RIDER_NUMBER + 1);
    }

    private RiderResponse fetchFindRider(final String resource, JwtTokenResponse tokenResponse) {
        return given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(resource)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(RiderResponse.class);
    }

    private String fetchCreateRider(JwtTokenResponse tokenResponse, RiderCreateRequest request) {
        return given()
            .header(createTokenHeader(tokenResponse))
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/riders")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("Location");
    }

    private void fetchFindRidersByMemberId(final Long memberId, final JwtTokenResponse tokenResponse) {
        final RiderResponses riders = given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/api/riders/members/{memberId}", memberId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .as(RiderResponses.class);

        assertThat(riders.getRiderResponses().size()).isEqualTo(RIDER_NUMBER + 1);
    }

    private void fetchUpdateRider(final String resource, final JwtTokenResponse tokenResponse) {
        final RiderUpdateRequest riderUpdateRequest = updateMockRequest();

        final String location = given()
            .header(createTokenHeader(tokenResponse))
            .body(riderUpdateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put(resource)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header("Location");

        assertThat(resource).isEqualTo(location);
    }

    private void fetchDeleteRider(final String resource, final JwtTokenResponse tokenResponse) {
        given()
            .header(createTokenHeader(tokenResponse))
            .when()
            .delete(resource)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void fetchFindRiderFailed(final String resource, final JwtTokenResponse tokenResponse) {
        given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(resource)
            .then()
            .log().all()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
