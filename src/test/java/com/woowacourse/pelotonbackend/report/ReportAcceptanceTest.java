package com.woowacourse.pelotonbackend.report;

import static com.woowacourse.pelotonbackend.report.domain.ReportFixture.*;
import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.report.presentation.ReportCreateContent;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportAcceptanceTest {
    @LocalServerPort
    public int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Given 신고하는 유저의 ID, 신고할 인증의 ID가 주어진다.
     * When 신고하는 유저가 인증에 포함되는 유저를 신고한다.
     * Then 신고가 완료된다.
     */
    @Test
    void createReport() throws JsonProcessingException {
        given()
            .log().all()
            .body(objectMapper.writeValueAsBytes(new ReportCreateContent(REPORT_TYPE, DESCRIPTION)))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/reports/certifications/{certificationId}/members/{reportMemberId}", CERTIFICATION_ID, MEMBER_ID)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", "/api/reports/1");
    }
}
