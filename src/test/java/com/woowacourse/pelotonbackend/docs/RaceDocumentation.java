package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static com.woowacourse.pelotonbackend.docs.DocumentFormatGenerator.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.apache.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.ResultHandler;

public class RaceDocumentation {
    public static RestDocumentationResultHandler createRace() {
        return document("race/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("title").type(STRING).description("레이스 제목"),
                fieldWithPath("description").type(STRING).description("레이스 세부내용"),
                fieldWithPath("raceDuration").description("레이스 기간"),
                fieldWithPath("raceDuration.startDate").attributes(getDateFormat()).description("레이스 시작 날짜"),
                fieldWithPath("raceDuration.endDate").attributes(getDateFormat()).description("레이스 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("레이스 종류"),
                subsectionWithPath("entranceFee").type(STRING).description("레이스 입장료")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler createBadRace() {
        return document("race/create-fail",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("title").type(NULL).description("레이스 제목"),
                fieldWithPath("description").type(NULL).description("레이스 세부내용"),
                fieldWithPath("raceDuration").type(OBJECT).description("레이스 기간"),
                fieldWithPath("raceDuration.startDate").type(STRING).attributes(getDateFormat()).description("레이스 시작 날짜"),
                fieldWithPath("raceDuration.endDate").type(STRING).attributes(getDateFormat()).description("레이스 종료 날짜"),
                fieldWithPath("category").type(NULL).description("레이스 종류"),
                subsectionWithPath("entranceFee").type(NULL).description("레이스 입장료")
            ),
            getErrorResponseFieldsWithFieldErrors()
        );
    }

    public static RestDocumentationResultHandler getRace() {
        return document("race/get-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("Race ID")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("Race Id"),
                fieldWithPath("title").type(STRING).description("레이스 제목"),
                fieldWithPath("description").type(STRING).description("레이스 상세내용"),
                fieldWithPath("thumbnail").type(OBJECT).description("썸네일 이미지"),
                fieldWithPath("thumbnail.baseImageUrl").type(STRING).description("썸네일 이미지 Url"),
                fieldWithPath("certificationExample").type(OBJECT).description("인증 예시 이미지"),
                fieldWithPath("certificationExample.baseImageUrl").type(STRING).description("인증 예시 이미지 Url"),
                fieldWithPath("raceDuration").type(OBJECT).description("Race 기간"),
                fieldWithPath("raceDuration.startDate").type(STRING).attributes(getDateFormat()).description("Race 시작 날짜"),
                fieldWithPath("raceDuration.endDate").type(STRING).attributes(getDateFormat()).description("Race 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("Race 종류"),
                subsectionWithPath("entranceFee").type(OBJECT).description("레이스 입장료")
            )
        );
    }

    public static RestDocumentationResultHandler getBadRace() {
        return document("race/get-bad-path",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            getErrorResponseFields()
        );
    }

    public static RestDocumentationResultHandler updateRace() {
        return document("race/update-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("Race ID")
            ),
            requestFields(
                fieldWithPath("title").type(STRING).description("레이스 제목"),
                fieldWithPath("description").type(STRING).description("레이스 세부내용"),
                fieldWithPath("raceDuration").description("레이스 기간"),
                fieldWithPath("raceDuration.startDate").attributes(getDateFormat()).description("레이스 시작 날짜"),
                fieldWithPath("raceDuration.endDate").attributes(getDateFormat()).description("레이스 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("레이스 종류"),
                fieldWithPath("certification").type(OBJECT).description("레이스 인증 예시"),
                fieldWithPath("certification.baseImageUrl").type(STRING).description("레이스 인증 예시 이미지 url"),
                fieldWithPath("thumbnail").type(OBJECT).description("레이스 썸네일 이미지"),
                fieldWithPath("thumbnail.baseImageUrl").type(STRING).description("레이스 썸네일 이미지 url"),
                subsectionWithPath("entranceFee").type(OBJECT).description("레이스 입장료")
            )
        );
    }

    public static RestDocumentationResultHandler updateBadPathRace() {
        return document("race/update-bad-path",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("id").description("Race ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("title").type(STRING).description("레이스 제목"),
                fieldWithPath("description").type(STRING).description("레이스 세부내용"),
                fieldWithPath("raceDuration").description("레이스 기간"),
                fieldWithPath("raceDuration.startDate").attributes(getDateFormat()).description("레이스 시작 날짜"),
                fieldWithPath("raceDuration.endDate").attributes(getDateFormat()).description("레이스 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("레이스 종류"),
                fieldWithPath("certification").type(OBJECT).description("레이스 인증 예시"),
                fieldWithPath("certification.baseImageUrl").type(STRING).description("레이스 인증 예시 이미지 url"),
                fieldWithPath("thumbnail").type(OBJECT).description("레이스 썸네일 이미지"),
                fieldWithPath("thumbnail.baseImageUrl").type(STRING).description("레이스 썸네일 이미지 url"),
                subsectionWithPath("entranceFee").type(OBJECT).description("레이스 입장료")
            ),
            getErrorResponseFields()
        );
    }

    public static RestDocumentationResultHandler updateBadRequestRace() {
        return document("race/update-bad-request",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("id").description("Race ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            ),
            getErrorResponseFields()
        );
    }

    public static RestDocumentationResultHandler updateNotExistRace() {
        return document("race/update-not-exist",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("id").description("Race ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            ),
            getErrorResponseFields());
    }

    public static RestDocumentationResultHandler deleteRace() {
        return document("race/delete-success",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("id").description("Race ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            ));
    }

    public static RestDocumentationResultHandler getNotExistRace() {
        return document("race/get-not-exist",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(parameterWithName("id").description("Member ID")),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            ),
            getErrorResponseFields()
        );
    }
}
