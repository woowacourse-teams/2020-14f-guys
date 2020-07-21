package com.woowacourse.pelotonbackend.member.acceptance;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberAcceptanceTest {
    private static final String RESOURCE_URL = "/api/members/";

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
        final MemberCreateRequest memberRequest = memberCreateRequest();
        final Long id = createMember(memberRequest);

        final MemberResponse memberResponse = findMember(id);

        assertAll(
            () -> assertThat(id).isEqualTo(memberResponse.getId()),
            () -> assertThat(memberRequest.getEmail()).isEqualTo(memberResponse.getEmail()),
            () -> assertThat(memberRequest.getName()).isEqualTo(memberResponse.getName()),
            () -> assertThat(memberRequest.getCash()).isEqualTo(memberResponse.getCash()),
            () -> assertThat(memberRequest.getRole()).isEqualTo(memberResponse.getRole())
        );

        final MemberCreateRequest memberOtherRequest = MemberFixture.memberCreateOtherRequest();
        createMember(memberOtherRequest);

        final List<MemberResponse> memberResponses = findAll().getResponses();

        assertAll(
            () -> assertThat(memberResponses.size()).isEqualTo(2),

            () -> assertThat(memberRequest.getEmail()).isEqualTo(memberResponses.get(0).getEmail()),
            () -> assertThat(memberRequest.getName()).isEqualTo(memberResponses.get(0).getName()),
            () -> assertThat(memberRequest.getCash()).isEqualTo(memberResponses.get(0).getCash()),
            () -> assertThat(memberRequest.getRole()).isEqualTo(memberResponses.get(0).getRole()),

            () -> assertThat(memberOtherRequest.getEmail()).isEqualTo(memberResponses.get(1).getEmail()),
            () -> assertThat(memberOtherRequest.getName()).isEqualTo(memberResponses.get(1).getName()),
            () -> assertThat(memberOtherRequest.getCash()).isEqualTo(memberResponses.get(1).getCash()),
            () -> assertThat(memberOtherRequest.getRole()).isEqualTo(memberResponses.get(1).getRole())
        );
    }

    private MemberResponses findAll() {
        return given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(RESOURCE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(MemberResponses.class);
    }

    private MemberResponse findMember(final Long id) {
        return given()
            .when()
            .get(String.format("%s%d", RESOURCE_URL, id))
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(MemberResponse.class);
    }

    private Long createMember(final MemberCreateRequest memberRequest) throws JsonProcessingException {
        final byte[] request = objectMapper.writeValueAsBytes(memberRequest);

        final String header = given()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(RESOURCE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("Location");

        return Long.parseLong(header.substring(RESOURCE_URL.length()));
    }
}
