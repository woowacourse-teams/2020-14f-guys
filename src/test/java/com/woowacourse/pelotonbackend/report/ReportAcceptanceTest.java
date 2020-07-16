package com.woowacourse.pelotonbackend.report;

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
import com.woowacourse.pelotonbackend.report.domain.ReportType;
import com.woowacourse.pelotonbackend.report.infra.ReportCreateBody;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportAcceptanceTest {
    public static final Long MEMBER_ID = 1L;

    public static final Long CERTIFICATION_ID = 5L;

    public static final ReportType REPORT_TYPE = ReportType.FAKE;

    public static final String DESCRIPTION = "설명";

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
            .body(objectMapper.writeValueAsBytes(new ReportCreateBody(REPORT_TYPE, DESCRIPTION))).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            when().
            post("/api/reports/certification/{certificationId}/member/{reportMemberId}", CERTIFICATION_ID, MEMBER_ID).
            then().
            log().all().
            statusCode(HttpStatus.CREATED.value()).
            header("Location", "/api/reports/1");
    }
}
