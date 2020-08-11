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
                fieldWithPath("race_duration").description("레이스 기간"),
                fieldWithPath("race_duration.start_date").attributes(getDateFormat()).description("레이스 시작 날짜"),
                fieldWithPath("race_duration.end_date").attributes(getDateFormat()).description("레이스 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("레이스 종류"),
                fieldWithPath("days").type(ARRAY).attributes(getDayFormat()).description("미션을 진행할 요일"),
                fieldWithPath("certification_available_duration").description("미션 인증이 가능한 시간"),
                fieldWithPath("certification_available_duration.start_time").attributes(getTimeFormat())
                    .description("인증 시작 시간"),
                fieldWithPath("certification_available_duration.end_time").attributes(getTimeFormat())
                    .description("인증 종료 시간"),
                subsectionWithPath("entrance_fee").type(STRING).description("레이스 입장료")
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
                fieldWithPath("race_duration").type(OBJECT).description("레이스 기간"),
                fieldWithPath("race_duration.start_date").type(STRING)
                    .attributes(getDateFormat())
                    .description("레이스 시작 날짜"),
                fieldWithPath("race_duration.end_date").type(STRING)
                    .attributes(getDateFormat())
                    .description("레이스 종료 날짜"),
                fieldWithPath("category").type(NULL).description("레이스 종류"),
                fieldWithPath("days").type(NULL).attributes(getDayFormat()).description("미션을 진행할 요일"),
                fieldWithPath("certification_available_duration").type(NULL).description("미션 인증이 가능한 시간"),
                subsectionWithPath("entrance_fee").type(NULL).description("레이스 입장료")
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
                fieldWithPath("thumbnail").type(STRING).description("썸네일 이미지"),
                fieldWithPath("certification_example").type(STRING).description("인증 예시 이미지"),
                fieldWithPath("race_duration").type(OBJECT).description("Race 기간"),
                fieldWithPath("race_duration.start_date").type(STRING)
                    .attributes(getDateFormat())
                    .description("Race 시작 날짜"),
                fieldWithPath("race_duration.end_date").type(STRING)
                    .attributes(getDateFormat())
                    .description("Race 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("Race 종류"),
                subsectionWithPath("entrance_fee").type(STRING).description("레이스 입장료")
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
                fieldWithPath("race_duration").description("레이스 기간"),
                fieldWithPath("race_duration.start_date").attributes(getDateFormat()).description("레이스 시작 날짜"),
                fieldWithPath("race_duration.end_date").attributes(getDateFormat()).description("레이스 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("레이스 종류"),
                fieldWithPath("certification").type(STRING).description("레이스 인증 예시"),
                fieldWithPath("thumbnail").type(STRING).description("레이스 썸네일 이미지"),
                subsectionWithPath("entrance_fee").type(STRING).description("레이스 입장료")
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
                fieldWithPath("race_duration").description("레이스 기간"),
                fieldWithPath("race_duration.start_date").attributes(getDateFormat()).description("레이스 시작 날짜"),
                fieldWithPath("race_duration.end_date").attributes(getDateFormat()).description("레이스 종료 날짜"),
                fieldWithPath("category").type(STRING).attributes(getRaceCategoryFormat()).description("레이스 종류"),
                fieldWithPath("certification").type(STRING).description("레이스 인증 예시"),
                fieldWithPath("thumbnail").type(STRING).description("레이스 썸네일 이미지"),
                subsectionWithPath("entrance_fee").type(STRING).description("레이스 입장료")
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
