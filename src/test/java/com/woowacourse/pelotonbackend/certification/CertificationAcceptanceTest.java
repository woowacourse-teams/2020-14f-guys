package com.woowacourse.pelotonbackend.certification;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CertificationAcceptanceTest {
    @LocalServerPort
    public int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @TestFactory
    public Stream<DynamicTest> certificationTest() {
        return Stream.of(
            DynamicTest.dynamicTest("Test certification create", () ->
                given()
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .multiPart("certification_image", TEST_FILE_NAME, TEST_FILE, MediaType.IMAGE_JPEG_VALUE)
                    .param("status", TEST_STATUS)
                    .param("description", TEST_DESCRIPTION)
                    .when()
                    .post("/api/certifications/riders/{riderId}/missions/{missionId}", TEST_RIDER_ID, TEST_MISSION_ID)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("location", String.format("/api/certifications/%d", TEST_CERTIFICATION_ID)))
        );
    }
}
