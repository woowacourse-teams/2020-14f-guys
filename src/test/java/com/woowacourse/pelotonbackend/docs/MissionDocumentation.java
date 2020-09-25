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

public class MissionDocumentation {
    public static RestDocumentationResultHandler createMission() {
        return document("mission/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("mission_duration").description("미션 수행 가능 시간"),
                fieldWithPath("mission_duration.start_time").attributes(getDateTimeFormat()).description("미션 수행 시작 시간"),
                fieldWithPath("mission_duration.end_time").attributes(getDateTimeFormat()).description("미션 수행 종료 시간"),
                fieldWithPath("mission_instruction").type(STRING).description("미션 수행 방법"),
                fieldWithPath("race_id").type(NUMBER).description("미션의 레이스 id")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler getMission() {
        return document("mission/get-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("Mission ID")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("Mission Id"),
                fieldWithPath("mission_duration").description("미션 수행 가능 시간"),
                fieldWithPath("mission_duration.start_time").attributes(getDateTimeFormat()).description("미션 수행 시작 시간"),
                fieldWithPath("mission_duration.end_time").attributes(getDateTimeFormat()).description("미션 수행 종료 시간"),
                fieldWithPath("mission_instruction").type(STRING).description("미션 수행 방법"),
                fieldWithPath("race_id").type(NUMBER).description("미션의 레이스 id")
            )
        );
    }

    public static RestDocumentationResultHandler updateMission() {
        return document("mission/update-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("Mission ID")
            ),
            requestFields(
                fieldWithPath("mission_duration").description("미션 수행 가능 시간"),
                fieldWithPath("mission_duration.start_time").attributes(getDateTimeFormat()).description("미션 수행 시작 시간"),
                fieldWithPath("mission_duration.end_time").attributes(getDateTimeFormat()).description("미션 수행 종료 시간"),
                fieldWithPath("mission_instruction").type(STRING).description("미션 수행 방법"),
                fieldWithPath("race_id").type(NUMBER).description("미션의 레이스 id")
            )
        );
    }

    public static RestDocumentationResultHandler deleteMission() {
        return document("mission/delete-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            )
        );
    }
}
