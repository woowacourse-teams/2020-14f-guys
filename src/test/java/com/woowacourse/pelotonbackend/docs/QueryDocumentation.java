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

public class QueryDocumentation {
    public static RestDocumentationResultHandler getRaces() {
        return document("queries/races/get-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            responseFields(
                fieldWithPath("race_responses").type(ARRAY).description("참가하고 있는 모든 RaceResponse 리스트"),
                fieldWithPath("race_responses[].id").type(NUMBER).description("Race Id"),
                fieldWithPath("race_responses[].title").type(STRING).description("레이스 제목"),
                fieldWithPath("race_responses[].description").type(STRING).description("레이스 상세내용"),
                fieldWithPath("race_responses[].thumbnail").type(STRING).description("썸네일 이미지 URL"),
                fieldWithPath("race_responses[].certification_example").type(STRING).description("인증 예시 이미지 URL"),
                fieldWithPath("race_responses[].race_duration").type(OBJECT).description("Race 기간"),
                fieldWithPath("race_responses[].race_duration.start_date").type(STRING).attributes(getDateFormat()).description("Race 시작 날짜"),
                fieldWithPath("race_responses[].race_duration.end_date").type(STRING).attributes(getDateFormat()).description("Race 종료 날짜"),
                fieldWithPath("race_responses[].category").type(STRING).attributes(getRaceCategoryFormat()).description("Race 종류"),
                subsectionWithPath("race_responses[].entrance_fee").type(STRING).description("레이스 입장료")
            )
        );
    }

    public static RestDocumentationResultHandler getRacesFail() {
        return document("queries/races/get-fail",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("Invalid Access Token"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            getErrorResponseFields()
        );
    }

    public static RestDocumentationResultHandler findCertificationsByRaceId() {
        return document("queries/get-certifications-race-id",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("raceId").description("레이스 아이디")
            ),
            responseFields(
                subsectionWithPath("certifications").type(OBJECT).description("전체 정보"),
                subsectionWithPath("certifications.content").type(ARRAY).description("해당 페이지의 컨텐츠"),
                subsectionWithPath("certifications.totalElements").type(NUMBER).description("총 컨텐츠의 양"),
                subsectionWithPath("certifications.pageable").type(OBJECT).description("페이지 관련 정보")
            )
        );
    }

    public static RestDocumentationResultHandler findCertificationsByNotExistRaceId() {
        return document("queries/get-certifications-race-not-exist",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("raceId").description("레이스 아이디")
            ),
            getErrorResponseFields()
        );
    }
}
