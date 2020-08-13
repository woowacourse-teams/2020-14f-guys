package com.woowacourse.pelotonbackend.member;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.support.AcceptanceTest;

public class MemberAcceptanceTest extends AcceptanceTest {
    /**
     * Feature: Member 관리
     *
     * Scenario: Member를 관리한다.
     *
     * Given: 생성할 Member의 kakaoId, profile, name, email, cash, role이 주어진다.
     * When: Member의 생성 요청을 보낸다.
     * Then: 새로운 Member가 생성된다.
     *
     * When: 생성한 Member를 찾는 요청을 보낸다.
     * Then: 해당 Member가 조회된다.
     *
     * When: 생성한 Member의 업데이트 요청을 보낸다.
     * Then: 해당 Member를 조회한다.
     * Then: 해당 Member가 업데이트된다.
     *
     * When: 생성한 Member의 삭제 요청을 보낸다.
     * Then: 해당 Member가 삭제된다.
     */

    @DisplayName("회원을 관리하는 기능")
    @Test
    void manageMember() throws FileNotFoundException {
        final MemberResponse memberResponse = createAndFindMember();
        updateName(memberResponse);
        updateCash(memberResponse);
        updateProfile(memberResponse);
        requestDelete(memberResponse);
    }

    private MemberResponse createAndFindMember() {
        final MemberCreateRequest memberRequest = createRequest(KAKAO_ID, EMAIL, NAME);
        final Long createMemberId = requestCreate(memberRequest);
        final MemberResponse memberResponse = requestFind(KAKAO_ID);

        assertAll(
            () -> assertThat(createMemberId).isEqualTo(memberResponse.getId()),
            () -> assertThat(memberRequest).isEqualToIgnoringGivenFields(memberResponse, "id")
        );

        final MemberCreateRequest memberOtherRequest = MemberFixture.createRequest(KAKAO_ID2, EMAIL2, NAME2);
        requestCreate(memberOtherRequest);
        final List<MemberResponse> memberResponses = requestFindAll(KAKAO_ID).getResponses();

        assertAll(
            () -> assertThat(memberResponses.size()).isEqualTo(2),
            () -> assertThat(memberRequest).isEqualToIgnoringGivenFields(memberResponses.get(0), "id"),
            () -> assertThat(memberOtherRequest).isEqualToIgnoringGivenFields(memberResponses.get(1), "id")
        );
        return memberResponse;
    }

    private void updateName(final MemberResponse memberResponse) {
        final MemberNameUpdateRequest nameUpdatedRequest = MemberFixture.createNameUpdateRequest();
        requestUpdateName(memberResponse.getKakaoId(), nameUpdatedRequest);
        final MemberResponse nameUpdatedResponse = requestFind(memberResponse.getKakaoId());

        assertAll(
            () -> assertThat(nameUpdatedResponse.getName()).isEqualTo(nameUpdatedRequest.getName()),
            () -> assertThat(nameUpdatedResponse).isEqualToIgnoringGivenFields(memberResponse, "name")
        );
    }

    private void updateCash(final MemberResponse memberResponse) {
        final MemberCashUpdateRequest cashUpdatedRequest = MemberFixture.createCashUpdateRequest();
        requestUpdateCash(memberResponse.getKakaoId(), cashUpdatedRequest);
        final MemberResponse cashUpdatedResponse = requestFind(memberResponse.getKakaoId());

        assertAll(
            () -> assertThat(cashUpdatedResponse.getCash()).isEqualTo(cashUpdatedRequest.getCash()),
            () -> assertThat(cashUpdatedResponse).isEqualToIgnoringGivenFields(memberResponse, "name", "cash")
        );
    }

    private void updateProfile(final MemberResponse memberResponse) throws FileNotFoundException {
        requestUpdateProfile(memberResponse.getKakaoId());
        final MemberResponse imageUpdatedResponse = requestFind(memberResponse.getKakaoId());

        assertThat(imageUpdatedResponse.getProfile().getBaseImageUrl()).contains(UPLOAD_SERVER_URL);
    }

    private void requestDelete(MemberResponse memberResponse) {
        requestDelete(memberResponse.getKakaoId());
        final MemberResponses responseAfterDelete = requestFindAll(KAKAO_ID2);

        assertThat(responseAfterDelete.getResponses()).hasSize(1);
    }

    private void requestDelete(final Long kakaoId) {
        given()
            .when()
            .header(createTokenHeader(kakaoId))
            .delete(RESOURCE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void requestUpdateProfile(final Long kakaoId) throws FileNotFoundException {
        final String FILE_PATH = "src/test/resources";
        final String FILE_NAME = "SampleFile.jpeg";
        final File FILE = new File(String.format("%s/%s", FILE_PATH, FILE_NAME));
        final FileInputStream fileInputStream = new FileInputStream(FILE);

        given()
            .when()
            .header(createTokenHeader(kakaoId))
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("profile_image", FILE_NAME, fileInputStream, MediaType.IMAGE_JPEG_VALUE)
            .post(RESOURCE_URL + "/profile")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    private void requestUpdateCash(final Long kakaoId, final MemberCashUpdateRequest cashUpdatedRequest) {
        given()
            .header(createTokenHeader(kakaoId))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(cashUpdatedRequest)
            .when()
            .patch(RESOURCE_URL + "/cash")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header("Location");
    }

    private void requestUpdateName(final Long kakaoId, final MemberNameUpdateRequest updateRequest) {
        given()
            .header(createTokenHeader(kakaoId))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(updateRequest)
            .when()
            .patch(RESOURCE_URL + "/name")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header("Location");
    }

    private MemberResponses requestFindAll(final Long kakaoId) {
        return given()
            .header(createTokenHeader(kakaoId))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(RESOURCE_URL + "/all")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(MemberResponses.class);
    }

}
