package com.woowacourse.pelotonbackend.member.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginAcceptanceTest {
    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    // @TestFactory
    // Stream<DynamicTest> login(){
    //     return Stream.of(
    //         DynamicTest.dynamicTest("Login Memeber", () -> {
    //             given()
    //                 .when()
    //                 .get("/api/login")
    //                 .then()
    //                 .log().all()
    //                 .statusCode(HttpStatus.OK.value());
    //         })
    //     );
    // }
}
