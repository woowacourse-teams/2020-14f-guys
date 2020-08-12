package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static com.woowacourse.pelotonbackend.docs.DocumentFormatGenerator.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

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
                fieldWithPath("raceResponses").type(ARRAY).description("참가하고 있는 모든 RaceResponse 리스트"),
                fieldWithPath("raceResponses[].id").type(NUMBER).description("Race Id"),
                fieldWithPath("raceResponses[].title").type(STRING).description("레이스 제목"),
                fieldWithPath("raceResponses[].description").type(STRING).description("레이스 상세내용"),
                fieldWithPath("raceResponses[].thumbnail").type(STRING).description("썸네일 이미지 URL"),
                fieldWithPath("raceResponses[].certification_example").type(STRING).description("인증 예시 이미지 URL"),
                fieldWithPath("raceResponses[].race_duration").type(OBJECT).description("Race 기간"),
                fieldWithPath("raceResponses[].race_duration.start_date").type(STRING).attributes(getDateFormat()).description("Race 시작 날짜"),
                fieldWithPath("raceResponses[].race_duration.end_date").type(STRING).attributes(getDateFormat()).description("Race 종료 날짜"),
                fieldWithPath("raceResponses[].category").type(STRING).attributes(getRaceCategoryFormat()).description("Race 종류"),
                subsectionWithPath("raceResponses[].entrance_fee").type(STRING).description("레이스 입장료")
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
            responseFields(
                fieldWithPath("status").type(NUMBER).description("Unauthorize status"),
                fieldWithPath("code").type(STRING).description("Error code"),
                fieldWithPath("message").type(STRING).description("Error message")
            )
        );
    }
}
