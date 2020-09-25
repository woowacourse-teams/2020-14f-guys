package com.woowacourse.pelotonbackend.certification.acceptance;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationDescriptionUpdateRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationStatusUpdateRequest;
import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.ErrorResponse;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;

public class CertificationAcceptanceTest extends AcceptanceTest {
    /*
     * Feature: Certification 관리
     *
     * Scenario: Certification를 관리한다.
     * Given: Member가 생성이 되어있다.(해당 멤버의 토큰이 발급되어 있다.)
     *        Race가 생성이 되어있다.
     *        Member는 Race에 참가 되어있다.(rider가 존재한다.)
     *        Mission이 생성 되어있다.
     * When: Member가 Race에 입장을 요청한다.(Certification 생성 요청)
     * Then: 새로운 Certification가 생성된다.
     *
     * Given: Certification가 존재한다.
     * When: Certification을 조회한다.
     * Then: Certification을 반환한다.
     *     *
     * Given: Certification의 상세 설명을 업데이트한다.
     * When: Certification을 조회한다.
     * Then: 업데이트된 Certification이 반환된다.
     *
     * Given: Certification의 상태를 업데이트한다.
     * When: Certification을 조회한다.
     * Then: 업데이트된 Certification이 반환된다.
     *
     * Given: Certification을 업데이트한다.
     * When: Certification을 조회한다.
     * Then: 업데이트된 Certification이 반환된다.

     * Given: Certification를 삭제한다.
     * When: Certification을 조회한다.
     * Then: Certification이 조회되지 않는다.
     */
    @DisplayName("Certification을 관리하는 인수테스트")
    @Test
    void manageCertification() {
        final JwtTokenResponse token = loginMember(
            MemberFixture.createRequest(MemberFixture.KAKAO_ID, MemberFixture.EMAIL, MemberFixture.NAME));
        final CertificationRequest createRequest = CertificationFixture.createMockCertificationRequest();
        final CertificationRequest updateCertificationRequest = updateMockCertificationRequest();
        final CertificationDescriptionUpdateRequest descriptionUpdateRequest = CertificationFixture.createDescriptionUpdateRequest();
        final CertificationStatusUpdateRequest statusUpdateRequest = CertificationFixture.createStatusUpdateRequest();

        final String resource = fetchCreateCertification(token, createRequest);
        final CertificationResponse response = fetchRetrieveCertification(token, resource);
        assertThat(createRequest).isEqualToIgnoringGivenFields(response, "id", "createdAt", "updatedAt", "description");

        createDuplicatedCertification(token, createRequest);

        fetchUpdateDescriptionCertification(token, descriptionUpdateRequest);
        final CertificationResponse descriptionUpdated = fetchRetrieveCertification(token, resource);
        assertThat(response).isEqualToIgnoringGivenFields(descriptionUpdated, "updatedAt", "description");
        assertThat(descriptionUpdated.getDescription()).isEqualTo(descriptionUpdated.getDescription());

        fetchUpdateStatusCertification(token, statusUpdateRequest);
        final CertificationResponse statusUpdated = fetchRetrieveCertification(token, resource);
        assertThat(statusUpdated).isEqualToIgnoringGivenFields(descriptionUpdated, "updatedAt", "status");
        assertThat(statusUpdated.getStatus().name()).isEqualTo(statusUpdateRequest.getStatus().name());

        fetchUpdateCertification(token, updateCertificationRequest);
        final CertificationResponse updatedCertification = fetchRetrieveCertification(token, resource);
        assertThat(updatedCertification).isEqualToComparingOnlyGivenFields(updateCertificationRequest,
            "status", "riderId", "missionId");
        assertThat(updatedCertification.getImage()).isNotEqualTo(response.getImage());

        fetchDeleteCertification(token, resource);
        fetchCertificationNotFound(token, resource);
    }

    private void createDuplicatedCertification(final JwtTokenResponse token,
        final CertificationRequest createRequest) {

        final ErrorResponse errorResponse = given()
            .header(createTokenHeader(token))
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("certification_image", TEST_CERTIFICATION_FILE_NAME, TEST_CERTIFICATION_FILE,
                MediaType.IMAGE_JPEG_VALUE)
            .param("status", createRequest.getStatus())
            .param("description", createRequest.getDescription())
            .param("riderId", createRequest.getRiderId())
            .param("missionId", createRequest.getMissionId())
            .when()
            .post("/api/certifications")
            .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .as(ErrorResponse.class);

        assertThat(errorResponse).extracting(ErrorResponse::getCode)
            .isEqualTo(ErrorCode.CERTIFICATION_DUPLICATE.getCode());
        assertThat(errorResponse).extracting(ErrorResponse::getMessage)
            .isEqualTo(String.format("Certification(rider id: %d, mission id: %d) already exists!",
                createRequest.getRiderId(), createRequest.getMissionId()));
    }

    private String fetchCreateCertification(
        final JwtTokenResponse token,
        final CertificationRequest createRequest) {
        return given()
            .header(createTokenHeader(token))
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("certification_image", TEST_CERTIFICATION_FILE_NAME, TEST_CERTIFICATION_FILE,
                MediaType.IMAGE_JPEG_VALUE)
            .param("status", createRequest.getStatus())
            .param("description", createRequest.getDescription())
            .param("riderId", createRequest.getRiderId())
            .param("missionId", createRequest.getMissionId())
            .when()
            .post("/api/certifications")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("Location");
    }

    private CertificationResponse fetchRetrieveCertification(final JwtTokenResponse token, final String resource) {
        return given()
            .header(createTokenHeader(token))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(resource)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(CertificationResponse.class);
    }

    private void fetchUpdateDescriptionCertification(
        final JwtTokenResponse token,
        final CertificationDescriptionUpdateRequest descriptionUpdateRequest) {
        given()
            .header(createTokenHeader(token))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(descriptionUpdateRequest)
            .when()
            .patch("/api/certifications/descriptions/1")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header("Location");
    }

    private void fetchUpdateStatusCertification(
        final JwtTokenResponse token,
        final CertificationStatusUpdateRequest statusUpdateRequest) {
        given()
            .header(createTokenHeader(token))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(statusUpdateRequest)
            .when()
            .patch("/api/certifications/status/1")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header("Location");
    }

    private void fetchUpdateCertification(final JwtTokenResponse token, final CertificationRequest request) {
        given()
            .header(createTokenHeader(token))
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("certification_image", TEST_UPDATED_CERTIFICATION_FILE_NAME,
                TEST_UPDATED_CERTIFICATION_FILE, MediaType.IMAGE_JPEG_VALUE)
            .param("status", request.getStatus())
            .param("description", request.getDescription())
            .param("riderId", request.getRiderId())
            .param("missionId", request.getMissionId())
            .when()
            .post("/api/certifications/update/1")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    private void fetchDeleteCertification(final JwtTokenResponse token, final String resource) {
        given()
            .header(createTokenHeader(token))
            .when()
            .delete(resource)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void fetchCertificationNotFound(final JwtTokenResponse token, final String resource) {
        given()
            .header(createTokenHeader(token))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(resource)
            .then()
            .log().all()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
