package com.woowacourse.pelotonbackend.rider.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RiderAcceptanceTest {
    @LocalServerPort
    public int port;

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

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
        final RiderCreateRequest mockRequest = RiderFixture.createMockRequest();

        given()
            .body(mockRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(RiderFixture.TEST_RIDER_URI)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", RiderFixture.TEST_RIDER_URI + RiderFixture.TEST_RIDER_ID);
    }
}
