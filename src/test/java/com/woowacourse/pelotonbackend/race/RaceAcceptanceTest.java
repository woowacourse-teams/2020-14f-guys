package com.woowacourse.pelotonbackend.race;

import static org.assertj.core.api.Assertions.*;

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

    @DisplayName("레이스를 관리한다.(생성, 조회, 수정, 삭제)")
    @Test
    void manageRace() {
        createRace();
        retrieveRace();
    }

    void createRace() {
        final RaceCreateRequest request = RaceFixture.createMockRequest();

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/api/races")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", "/api/races/1");
    }

    void retrieveRace() {
        final RaceRetrieveResponse responseBody = given()
            .when()
            .get("/api/races/1")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .as(RaceRetrieveResponse.class);

        assertThat(responseBody).isEqualToIgnoringGivenFields(RaceFixture.retrieveResponse(), "thumbnail",
            "certificationExample");
    }
}
