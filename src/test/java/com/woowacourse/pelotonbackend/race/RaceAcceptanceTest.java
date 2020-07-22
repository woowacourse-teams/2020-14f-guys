package com.woowacourse.pelotonbackend.race;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.RaceCreateRequest;
import com.woowacourse.pelotonbackend.race.presentation.RaceRetrieveResponse;
import com.woowacourse.pelotonbackend.race.presentation.RaceUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RaceAcceptanceTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

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
     * When: 생성한 Race의 업데이트 요청을 보낸다..
     * Then: 해당 Race가 업데이트 된다.
     */
    @DisplayName("레이스를 관리한다.(생성, 조회, 수정, 삭제)")
    @Test
    void manageRace() {
        final String raceLocation = createRace();

        retrieveRaceAndCompareTo(raceLocation, RaceFixture.retrieveResponse());

        updateRace(raceLocation);
        retrieveRaceAndCompareTo(raceLocation, RaceFixture.retrieveUpdatedResponse());
    }

    String createRace() {
        final RaceCreateRequest request = RaceFixture.createMockRequest();

        final String location = given()
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

    void retrieveRaceAndCompareTo(final String resourceLocation, final RaceRetrieveResponse expected) {
        final RaceRetrieveResponse responseBody = given()
            .when()
            .get(resourceLocation)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .as(RaceRetrieveResponse.class);

        assertThat(responseBody).isEqualToIgnoringGivenFields(expected, "thumbnail", "certificationExample");
    }

    void updateRace(final String resourceLocation) {
        final RaceUpdateRequest raceUpdateRequest = RaceFixture.updateRequest();

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(raceUpdateRequest)
            .when()
            .put(resourceLocation)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }
}
