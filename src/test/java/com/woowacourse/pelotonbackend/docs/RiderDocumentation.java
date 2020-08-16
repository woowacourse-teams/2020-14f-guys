package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpHead;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.ResultHandler;

public class RiderDocumentation {
    public static RestDocumentationResultHandler createRider() {
        return document("rider/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("race_id").type(NUMBER).description("해당 Rider가 참여한 Race ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler getRider() {
        return document("rider/get-success",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("riderId").description("Rider ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("Rider ID"),
                fieldWithPath("member_id").type(NUMBER).description("Member ID"),
                fieldWithPath("race_id").type(NUMBER).description("Race ID"),
                fieldWithPath("created_at").description("생성된 시간")
            )
        );
    }

    public static RestDocumentationResultHandler getNotExistRider() {
        return document("rider/get-not-exist",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("riderId").description("Rider ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            getErrorResponseFields()
        );
    }

    public static RestDocumentationResultHandler getAllRidersInRace() {
        return document("rider/get-all-in-race",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("raceId").description("Race ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            responseFields(
                subsectionWithPath("rider_responses").type(ARRAY).description("RiderResponses")
            )
        );
    }

    public static RestDocumentationResultHandler getAllRidersOfMember() {
        return document("rider/get-all-of-member",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("memberId").description("Member ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            responseFields(
                fieldWithPath("rider_responses").type(ARRAY).description("RiderResponses"),
                fieldWithPath("rider_responses[].id").type(NUMBER).description("Rider ID"),
                fieldWithPath("rider_responses[].member_id").type(NUMBER).description("Rider의 Member ID"),
                fieldWithPath("rider_responses[].race_id").type(NUMBER).description("Rider가 참여하는 RaceID"),
                fieldWithPath("rider_responses[].created_at").type(STRING).description("Race에 참가한 시간")
            )
        );
    }

    public static RestDocumentationResultHandler updateRider() {
        return document("rider/update-success",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("riderId").description("Rider ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("race_id").type(NUMBER).description("해당 Rider가 참여한 Race ID"),
                fieldWithPath("member_id").type(NUMBER).description("해당 Rider의 Member ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler deleteRider() {
        return document("rider/delete-success",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("riderId").description("Rider ID")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler createDuplicatedRider() {
        return document("rider/create-duplicated",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("race_id").type(NUMBER).description("해당 Rider가 참여한 Race ID")
            ),
            getErrorResponseFields()
        );
    }
}
