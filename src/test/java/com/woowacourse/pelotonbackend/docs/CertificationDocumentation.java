package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class CertificationDocumentation {
    public static RestDocumentationResultHandler createCertification() {
        return document("certification/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestParts(
                partWithName("certification_image").description("인증 사진")
            ),
            requestParameters(
                parameterWithName("status").description("Certification 성공 여부"),
                parameterWithName("description").description("인증 세부내용").optional(),
                parameterWithName("riderId").description("인증 라이더 ID"),
                parameterWithName("missionId").description("미션 ID")
            ),
            responseHeaders(
                headerWithName("Location").description("Location of created certification")
            )
        );
    }

    public static RestDocumentationResultHandler createBadCertification() {
        return document("certification/create-fail",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestParts(
                partWithName("certification_image").description("인증 사진")
            ),
            requestParameters(
                parameterWithName("status").description("Certification 성공 여부"),
                parameterWithName("description").description("인증 세부내용").optional(),
                parameterWithName("riderId").description("인증 라이더 ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            getErrorResponseFieldsWithFieldErrors()
        );
    }

    public static RestDocumentationResultHandler getCertification() {
        return document("certification/get-certification",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("certification id")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("Certification id"),
                subsectionWithPath("image").type(OBJECT).description("Certification image"),
                fieldWithPath("status").type(STRING).description("Certification status(fail, success, reported)"),
                fieldWithPath("missionId").type(NUMBER).description("Certification mission id"),
                fieldWithPath("riderId").type(NUMBER).description("Certification rider id"),
                fieldWithPath("description").type(STRING).description("Certification detail")
            )
        );
    }

    public static RestDocumentationResultHandler getBadCertification() {
        return document("certification/get-not-found",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("certification id")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            responseFields(
                fieldWithPath("status").type(NUMBER).description("Error status code"),
                fieldWithPath("code").type(STRING).description("Error code"),
                fieldWithPath("message").type(STRING).description("Error message")
            )
        );
    }

    public static RestDocumentationResultHandler getByRiderId() {
        return document("certification/get-certification-riderId",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("rider id")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestParameters(
                parameterWithName("page").description("dss"),
                parameterWithName("size").description("dss")
            ),
            responseFields(
                subsectionWithPath("certifications").type(OBJECT).description("all info"),
                subsectionWithPath("certifications.content").type(ARRAY).description("contents on the page"),
                subsectionWithPath("certifications.totalElements").type(NUMBER).description("total amount of contents"),
                subsectionWithPath("certifications.pageable").type(OBJECT).description("info about paging")
            )
        );
    }

    public static RestDocumentationResultHandler updateDescription() {
        return document("certification/update-description",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
            ),
            pathParameters(
                parameterWithName("id").description("certification id")
            ),
            requestFields(
                fieldWithPath("description").description("description to update")
            ),
            responseHeaders(
                headerWithName("Location").description("Updated resource URL")
            )
        );
    }

    public static RestDocumentationResultHandler updateStatus() {
        return document("certification/update-status",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
            ),
            pathParameters(
                parameterWithName("id").description("rider id")
            ),
            requestFields(
                fieldWithPath("status").description("status to update")
            ),
            responseHeaders(
                headerWithName("Location").description("Updated resource URL")
            )
        );
    }

    public static RestDocumentationResultHandler deleteById() {
        return document("certification/delete",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            ),
            pathParameters(
                parameterWithName("id").description("certification id")
            )
        );
    }
}
