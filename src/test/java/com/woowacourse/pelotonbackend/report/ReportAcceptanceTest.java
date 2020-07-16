package com.woowacourse.pelotonbackend.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.woowacourse.pelotonbackend.report.domain.ReportType;
import com.woowacourse.pelotonbackend.report.infra.ReportCreateBody;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ReportAcceptanceTest {
    public static final Long MEMBER_ID = 1L;

    public static final Long CERTIFICATION_ID = 5L;

    public static final ReportType REPORT_TYPE = ReportType.FAKE;

    public static final String DESCRIPTION = "설명";

    @Autowired
    private WebTestClient webTestClient;

    /**
     * Given 신고하는 유저의 ID, 신고할 인증의 ID가 주어진다.
     * When 신고하는 유저가 인증에 포함되는 유저를 신고한다.
     * Then 신고가 완료된다.
     */
    @Test
    void createReport() {
        webTestClient.post()
            .uri("/api/reports/certification/{certificationId}/member/{reportMemberId}", CERTIFICATION_ID, MEMBER_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new ReportCreateBody(REPORT_TYPE, DESCRIPTION))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectHeader()
            .valueMatches("Location", "/api/reports/[0-9]+");
    }
}
