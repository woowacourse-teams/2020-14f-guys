package com.woowacourse.pelotonbackend.member.acceptance;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static com.woowacourse.pelotonbackend.member.presentation.MemberControllerTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberAcceptanceTest {

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

        when : 회원 전체 정보를 조회한다.
        then : 회원 전체 정보가 조회되었다.

        when : 회원을 변경했을 때
        then : 회원 정보를 읽어온다.
        then : 회원 정보가 변경 정보와 일치한다.

        when : 회원을 삭제한다.
        then: 기존 회원이 삭제되었다.
     */

    @DisplayName("회원을 관리하는 기능")
    @Test
    void manageMember() {
        final MemberResponse memberResponse = createAndFindMember();
        updateName(memberResponse);
        updateCash(memberResponse);
        requestDelete(memberResponse);
    }

    private MemberResponse createAndFindMember() {
        final MemberCreateRequest memberRequest = createRequest(EMAIL, NAME);
        final Long createMemberId = requestCreate(memberRequest);
        final MemberResponse memberResponse = requestFind(createMemberId);

        assertAll(
            () -> assertThat(createMemberId).isEqualTo(memberResponse.getId()),
            () -> assertThat(memberRequest).isEqualToIgnoringGivenFields(memberResponse, "id")
        );

        final MemberCreateRequest memberOtherRequest = MemberFixture.createRequest(EMAIL2, NAME2);
        requestCreate(memberOtherRequest);
        final List<MemberResponse> memberResponses = requestFindAll().getResponses();

        assertAll(
            () -> assertThat(memberResponses.size()).isEqualTo(2),
            () -> assertThat(memberRequest).isEqualToIgnoringGivenFields(memberResponses.get(0), "id"),
            () -> assertThat(memberOtherRequest).isEqualToIgnoringGivenFields(memberResponses.get(1), "id")
        );
        return memberResponse;
    }

    private void updateName(final MemberResponse memberResponse) {
        final MemberNameUpdateRequest nameUpdatedRequest = MemberFixture.createNameUpdateRequest();
        final Long updatedMemberId = requestUpdateName(memberResponse.getId(), nameUpdatedRequest);
        final MemberResponse nameUpdatedResponse = requestFind(updatedMemberId);

        assertAll(
            () -> assertThat(nameUpdatedResponse.getName()).isEqualTo(nameUpdatedRequest.getName()),
            () -> assertThat(nameUpdatedResponse).isEqualToIgnoringGivenFields(memberResponse, "name")
        );
    }

    private void updateCash(final MemberResponse memberResponse) {
        final MemberCashUpdateRequest cashUpdatedRequest = MemberFixture.createCashUpdateRequest();
        final Long cashUpdatedMemberId = requestUpdateCash(memberResponse.getId(), cashUpdatedRequest);
        final MemberResponse cashUpdatedResponse = requestFind(cashUpdatedMemberId);

        assertAll(
            () -> assertThat(cashUpdatedResponse.getCash()).isEqualTo(cashUpdatedRequest.getCash()),
            () -> assertThat(cashUpdatedResponse).isEqualToIgnoringGivenFields(memberResponse, "name","cash")
        );
    }

    private void requestDelete(final MemberResponse memberResponse) {
        requestCreate(MemberFixture.createRequest(EMAIL3, NAME3));
        requestDelete(memberResponse.getId());
        final MemberResponses responseAfterDelete = requestFindAll();

        assertThat(responseAfterDelete.getResponses()).hasSize(2);
    }

    private void requestDelete(final Long id) {
        given()
            .when()
            .delete(String.format("%s%d", RESOURCE_URL, id))
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private Long requestUpdateCash(final Long id, final MemberCashUpdateRequest cashUpdatedRequest) {
        final String location = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(cashUpdatedRequest)
            .when()
            .patch(String.format("%s%d/cash", RESOURCE_URL, id))
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header("Location");

        final long resourceId = Long.parseLong(location.substring(RESOURCE_URL.length()));
        return resourceId;
    }

    private Long requestUpdateName(final Long id, final MemberNameUpdateRequest updateRequest) {
        final String location = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(updateRequest)
            .when()
            .patch(String.format("%s%d/name", RESOURCE_URL, id))
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header("Location");

        final long resourceId = Long.parseLong(location.substring(RESOURCE_URL.length()));
        return resourceId;
    }

    private MemberResponses requestFindAll() {
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

    private MemberResponse requestFind(final Long id) {
        return given()
            .when()
            .get(String.format("%s%d", RESOURCE_URL, id))
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(MemberResponse.class);
    }

    private Long requestCreate(final MemberCreateRequest memberRequest) {
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

        final long resourceId = Long.parseLong(header.substring(RESOURCE_URL.length()));
        return resourceId;
    }
}
