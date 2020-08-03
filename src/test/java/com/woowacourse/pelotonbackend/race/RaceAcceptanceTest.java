package com.woowacourse.pelotonbackend.race;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceRetrieveResponse;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceUpdateRequest;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;
import io.restassured.response.ValidatableResponse;

public class RaceAcceptanceTest extends AcceptanceTest {
    /*
     * Feature: Race 관리
     *
     * Scenario: Race를 관리한다.
     * Given: 생성할 Race의 title, description, category, total amount, duration이 주어진다.
     * When: Race의 생성 요청을 보낸다.
     * Then: 새로운 Race가 생성된다.
     *
     * When: 생성한 Race를 찾는 요청을 보낸다.
     * Then: 해당 Race가 조회된다.
     *
     * When: 생성한 Race의 업데이트 요청을 보낸다.
     * Then: 해당 Race가 업데이트된다.
     *
     * When: 생성한 Race의 삭제 요청을 보낸다.
     * Then: 해당 Race가 삭제된다.
     */
    @DisplayName("레이스를 관리한다.(생성, 조회, 수정, 삭제)")
    @Test
    void manageRace() {
        final JwtTokenResponse tokenResponse = loginMember(
            MemberFixture.createRequest(MemberFixture.KAKAO_ID, MemberFixture.EMAIL, MemberFixture.NAME));
        final String raceLocation = createRace(tokenResponse);
        retrieveRaceAndCompareTo(raceLocation, RaceFixture.retrieveResponse(), tokenResponse);

        updateRace(raceLocation, tokenResponse);
        retrieveRaceAndCompareTo(raceLocation, RaceFixture.retrieveUpdatedResponse(), tokenResponse);

        deleteRace(raceLocation, tokenResponse);
        retrieveRaceNotFound(raceLocation, tokenResponse);
    }

    String createRace(final JwtTokenResponse tokenResponse) {
        final RaceCreateRequest request = RaceFixture.createMockRequest();

        final String location = given()
            .header(createTokenHeader(tokenResponse))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post(RACE_API_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("Location");

        assertThat(location).containsPattern(Pattern.compile(String.format("%s/[0-9]+", RACE_API_URL)));

        return location;
    }

    void retrieveRaceAndCompareTo(final String resourceLocation, final RaceRetrieveResponse expected,
        final JwtTokenResponse tokenResponse) {
        final RaceRetrieveResponse responseBody = retrieveRaceWithStatusCode(resourceLocation, tokenResponse,
            HttpStatus.OK.value())
            .extract()
            .body()
            .as(RaceRetrieveResponse.class);

        assertThat(responseBody).isEqualToIgnoringGivenFields(expected, "thumbnail", "certificationExample");
    }

    private ValidatableResponse retrieveRaceWithStatusCode(final String resourceLocation,
        final JwtTokenResponse tokenResponse, final int statusCode) {
        return given()
            .header(createTokenHeader(tokenResponse))
            .when()
            .get(resourceLocation)
            .then()
            .log().all()
            .statusCode(statusCode);
    }

    void updateRace(final String resourceLocation, final JwtTokenResponse tokenResponse) {
        final RaceUpdateRequest raceUpdateRequest = RaceFixture.updateRequest();

        given()
            .header(createTokenHeader(tokenResponse))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(raceUpdateRequest)
            .when()
            .put(resourceLocation)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    void deleteRace(final String resourceLocation, final JwtTokenResponse tokenResponse) {
        given()
            .header(createTokenHeader(tokenResponse))
            .when()
            .delete(resourceLocation)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    void retrieveRaceNotFound(final String resourceLocation, final JwtTokenResponse tokenResponse) {
        retrieveRaceWithStatusCode(resourceLocation, tokenResponse, HttpStatus.NOT_FOUND.value());
    }
}
