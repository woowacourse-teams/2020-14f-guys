package com.woowacourse.pelotonbackend.support;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
public class AcceptanceTest {
    @LocalServerPort
    public int port;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    protected static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected JwtTokenResponse loginMember(MemberCreateRequest request) {
        requestCreate(request);

        final String token = jwtTokenProvider.createToken(String.valueOf(request.getKakaoId()));
        return JwtTokenResponse.of(token, ADMIT);
    }

    protected Long requestCreate(final MemberCreateRequest memberRequest) {
        final String header = given()
            .body(memberRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(RESOURCE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("Location");

        final long resourceId = Long.parseLong(header.substring(RESOURCE_URL.length() + 1));
        return resourceId;
    }

    protected MemberResponse requestFind(final Long kakaoId) {
        return given()
            .header(createTokenHeader(kakaoId))
            .when()
            .get(RESOURCE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(MemberResponse.class);
    }

    protected Header createTokenHeader(final Long kakaoId) {
        return new Header("Authorization", "Bearer " + jwtTokenProvider.createToken(String.valueOf(kakaoId)));
    }

    protected Header createTokenHeader(final JwtTokenResponse tokenResponse) {
        return new Header("Authorization", "Bearer " + tokenResponse.getAccessToken());
    }
}
