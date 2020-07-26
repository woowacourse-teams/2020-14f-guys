package com.woowacourse.pelotonbackend.member.acceptance;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginAcceptanceTest {
    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @TestFactory
    @Disabled
    Stream<DynamicTest> login() {
        return Stream.of(
            DynamicTest.dynamicTest("Login Memeber", () ->
                given()
                    .when()
                    .get("/api/login")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value())
            )
        );
    }
}
