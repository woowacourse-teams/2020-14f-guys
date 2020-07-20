package com.woowacourse.pelotonbackend.member.acceptance;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.vo.Cash;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberAcceptanceTest {
    // @formatter:off

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

    /*
    Scenario : 회원을 관리한다.
        when : 회원을 만든다.
        then : 회원이 생성된다.

        when : 회원 정보를 읽어온다.
        then : 회원 정보가 생성 정보와 일치한다.

        when : 회원을 변경했을 때
        then : 회원 정보를 읽어온다.
        then : 회원 정보가 변경 정보와 일치한다.

        when : 회원을 삭제한다.
        then: 기존 회원이 삭제되었다.
     */

    @DisplayName("회원을 관리하는 기능")
    @Test
    void manageMember() throws JsonProcessingException {
        createMember(EMAIL, NAME, CASH, ROLE);
    }

    private void createMember(String email, String name, Cash cash, Role role) throws JsonProcessingException{
        final MemberCreateRequest memberCreateRequest = MemberFixture.memberCreateRequest();
        final byte[] request = objectMapper.writeValueAsBytes(memberCreateRequest);

        given()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/members")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("location", String.format("/api/members/%d", ID));
    }

    // @formatter:on
}
