package com.woowacourse.pelotonbackend.certification;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.hamcrest.Matchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;

public class CertificationAcceptanceTest extends AcceptanceTest {
    /**
     * Feature: Certification 관리
     *
     * Scenario: Certification 관리한다.
     *
     * Given: Rider가 생성이 되어있다.
     *        Mission이 생성이 되어있다.
     * When: Rider가 Mission에 인증을 요청한다.(Certification 생성 요청)
     * Then: 새로운 Certification 생성된다.
     */
    @TestFactory
    public Stream<DynamicTest> certificationTest() {
        final JwtTokenResponse tokenResponse = loginMember(
            MemberFixture.createRequest(MemberFixture.KAKAO_ID, MemberFixture.EMAIL, MemberFixture.NAME));
        return Stream.of(
            DynamicTest.dynamicTest("Certification 생성", () ->
                given()
                    .header(createTokenHeader(tokenResponse))
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .multiPart("certification_image", TEST_CERTIFICATION_FILE_NAME, TEST_CERTIFICATION_FILE,
                        MediaType.IMAGE_JPEG_VALUE)
                    .param("status", TEST_CERTIFICATION_STATUS)
                    .param("description", TEST_CERTIFICATION_DESCRIPTION)
                    .param("riderId", TEST_RIDER_ID)
                    .param("missionId", TEST_MISSION_ID)
                    .when()
                    .post("/api/certifications")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("location", String.format("/api/certifications/%d", TEST_CERTIFICATION_ID))
            ),
            DynamicTest.dynamicTest("잘못된 Certification 생성", () ->
                given()
                    .header(createTokenHeader(tokenResponse))
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .multiPart("certification_image", TEST_CERTIFICATION_FILE_NAME, TEST_CERTIFICATION_FILE,
                        MediaType.IMAGE_JPEG_VALUE)
                    .param("status", TEST_CERTIFICATION_STATUS)
                    .param("description", TEST_CERTIFICATION_DESCRIPTION)
                    .param("riderId", TEST_RIDER_ID)
                    .when()
                    .post("/api/certifications")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("status", equalTo(ErrorCode.INVALID_VALIDATE.getStatus()))
                    .body("errors", notNullValue())
            )
        );
    }
}
