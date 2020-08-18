package com.woowacourse.pelotonbackend.member.presentation;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.woowacourse.pelotonbackend.common.ErrorResponse;
import com.woowacourse.pelotonbackend.support.JwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.http.Header;

/**
 * ArgumentResolver에서 터진 예외가 GlobalExceptionHandler에서 잡히는지 확인하기 위한 통합 테스트
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginMemberArgumentResolverIntegrationTest {
    private static final String NOT_EXIST_KAKAO_ID = "1000";

    @LocalServerPort
    private int port;

    private final JwtTokenProvider jwtTokenProvider;

    public LoginMemberArgumentResolverIntegrationTest(
        @Value("${secrets.jwt.token.secret-key}") final String secretKey,
        @Value("${secrets.jwt.token.expire-length}") final long expireLength) {
        this.jwtTokenProvider = new JwtTokenProvider(secretKey, expireLength);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("존재하지 않는 kakaoId로 Member를 찾으려 할 때 MemberNotFoundExcetpion")
    @Test
    void notExistKakaoIdMemberRetrieve() {
        final String token = jwtTokenProvider.createToken(NOT_EXIST_KAKAO_ID);
        final ErrorResponse errorResponse = given()
            .log().all()
            .header(new Header("Authorization", "Bearer " + token))
            .when()
            .get("/api/members")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .body().as(ErrorResponse.class);

        assertThat(errorResponse.getCode()).isEqualTo("Member-001");
    }
}
