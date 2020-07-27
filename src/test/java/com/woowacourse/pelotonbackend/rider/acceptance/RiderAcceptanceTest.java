package com.woowacourse.pelotonbackend.rider.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;

public class RiderAcceptanceTest extends AcceptanceTest {
    /*
     * Feature: Rider 관리
     *
     * Scenario: Rider를 관리한다.
     * Given: Member가 생성이 되어있다.
     *        Race가 생성이 되어있다.
     * When: Member가 Race에 입장을 요청한다.(Rider 생성 요청)
     * Then: 새로운 Rider가 생성된다.
     */
    @DisplayName("Rider 관리 기능")
    @Test
    void manageRider() {
        final Long memberId = 1L;
        final Long raceId = 1L;
        final JwtTokenResponse tokenResponse = loginMember(
            MemberFixture.createRequest(MemberFixture.KAKAO_ID, MemberFixture.EMAIL, MemberFixture.NAME));

        final RiderCreateRequest riderCreateRequest = new RiderCreateRequest(memberId, raceId);

        given()
            .header(createTokenHeader(tokenResponse))
            .body(riderCreateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/riders")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", "/api/riders/1");
    }
}
