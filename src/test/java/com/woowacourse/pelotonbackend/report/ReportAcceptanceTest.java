package com.woowacourse.pelotonbackend.report;

import static com.woowacourse.pelotonbackend.report.domain.ReportFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;
import com.woowacourse.pelotonbackend.vo.JwtTokenResponse;

public class ReportAcceptanceTest extends AcceptanceTest {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Given 신고하는 유저의 ID, 신고할 인증의 ID가 주어진다.
     * When 신고하는 유저가 인증에 포함되는 유저를 신고한다.
     * Then 신고가 완료된다.
     */
    @DisplayName("Report CRUD 인수 테스트")
    @Test
    void reportCrud() throws JsonProcessingException {
        final JwtTokenResponse tokenResponse = loginMember(
            MemberFixture.createRequest(MemberFixture.KAKAO_ID, MemberFixture.EMAIL, MemberFixture.NAME));

        given()
            .log().all()
            .header(createTokenHeader(tokenResponse))
            .body(objectMapper.writeValueAsBytes(createRequestContent()))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/reports")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", "/api/reports/1");
    }
}
