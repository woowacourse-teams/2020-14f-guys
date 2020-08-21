package com.woowacourse.pelotonbackend.support;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
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

    protected JwtTokenResponse loginMember(final MemberCreateRequest request) {
        createMember(request);

        final String token = jwtTokenProvider.createToken(String.valueOf(request.getKakaoId()));
        return JwtTokenResponse.of(token, ADMIT);
    }

    protected List<JwtTokenResponse> loginMembers(final List<MemberCreateRequest> requests) {
        requests.forEach(this::createMember);

        return requests.stream()
            .map(request -> JwtTokenResponse.of(jwtTokenProvider.createToken(String.valueOf(request.getKakaoId())),
                ADMIT))
            .collect(Collectors.toList());
    }

    protected Long createMember(final MemberCreateRequest memberRequest) {
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

    protected MemberResponse findMember(final Long kakaoId) {
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

    protected MemberResponse findMember(final JwtTokenResponse tokenResponse) {
        return given()
            .header(createTokenHeader(tokenResponse))
            .when()
            .get(RESOURCE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(MemberResponse.class);
    }

    protected MemberResponses findAllMembers(final JwtTokenResponse tokenResponse) {
        return given()
            .header(createTokenHeader(tokenResponse))
            .when()
            .get(RESOURCE_URL + "/all")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(MemberResponses.class);
    }

    protected Header createTokenHeader(final Long kakaoId) {
        return new Header("Authorization", "Bearer " + jwtTokenProvider.createToken(String.valueOf(kakaoId)));
    }

    protected Header createTokenHeader(final JwtTokenResponse tokenResponse) {
        return new Header("Authorization", "Bearer " + tokenResponse.getAccessToken());
    }

    protected long createRace(final RaceCreateRequest request, final JwtTokenResponse tokenResponse) {
        final String location = given()
            .header(createTokenHeader(tokenResponse))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/api/races")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract().header("Location");
        final String[] splitLocation = location.split("/");

        return Long.parseLong(splitLocation[splitLocation.length - 1]);
    }

    private String createRider(JwtTokenResponse tokenResponse, RiderCreateRequest request) {
        return given()
            .header(createTokenHeader(tokenResponse))
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/riders")
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("Location");
    }

    protected void createRiders(final List<JwtTokenResponse> tokenResponses,
        final RiderCreateRequest riderCreateRequest) {

        tokenResponses.forEach(token -> createRider(token, riderCreateRequest));
    }

    protected List<RiderResponse> findAllRiders(final Long raceId, final JwtTokenResponse tokenResponse) {
        return given()
            .header(createTokenHeader(tokenResponse))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/api/riders/races/{id}", raceId)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(RiderResponses.class).getRiderResponses();
    }

    protected void createCertification(final JwtTokenResponse token, final CertificationRequest createRequest) {
        given()
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

    protected void createCertifications(final JwtTokenResponse token, final List<CertificationRequest> requests) {
        requests.forEach(request -> createCertification(token, request));
    }
}
