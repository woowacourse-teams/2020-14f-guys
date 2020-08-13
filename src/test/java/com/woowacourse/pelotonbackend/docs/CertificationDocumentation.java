package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static com.woowacourse.pelotonbackend.docs.DocumentFormatGenerator.*;
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
                parameterWithName("status").description("인증 성공 여부"),
                parameterWithName("description").description("인증 세부내용").optional(),
                parameterWithName("riderId").description("인증 라이더 ID"),
                parameterWithName("missionId").description("미션 ID")
            ),
            responseHeaders(
                headerWithName("Location").description("생성된 리소스 정보")
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
                parameterWithName("status").description("인증 성공 여부"),
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
                parameterWithName("id").description("인증 ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("인증 ID"),
                subsectionWithPath("image").type(STRING).description("인증한 이미지"),
                fieldWithPath("status").type(STRING).description("인증 상태(성공,실패,신고)"),
                fieldWithPath("mission_id").type(NUMBER).description("인증한 미션에 대한 아이디"),
                fieldWithPath("rider_id").type(NUMBER).description("인증한 라이더의 아이디"),
                fieldWithPath("description").type(STRING).description("인증에 대한 상세 설명")
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
                parameterWithName("id").description("인증 ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            getErrorResponseFields()
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
                parameterWithName("id").description("라이더의 ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestParameters(
                parameterWithName("page").description("응답 받을 페이지 번호"),
                parameterWithName("size").description("한 페이지에 들어갈 컨텐츠의 갯수")
            ),
            responseFields(
                subsectionWithPath("certifications").type(OBJECT).description("전체 정보"),
                subsectionWithPath("certifications.content").type(ARRAY).description("해당 페이지의 컨텐츠"),
                subsectionWithPath("certifications.totalElements").type(NUMBER).description("총 컨텐츠의 양"),
                subsectionWithPath("certifications.pageable").type(OBJECT).description("페이지 관련 정보")
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
                parameterWithName("id").description("인증 ID")
            ),
            requestFields(
                fieldWithPath("description").description("변경될 인증 상세 설명")
            ),
            responseHeaders(
                headerWithName("Location").description("수정이 완료된 리소스 값")
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
                parameterWithName("id").description("라이더 ID")
            ),
            requestFields(
                fieldWithPath("status").description("변경될 인증 상태값").attributes(getCertificationStatus())
            ),
            responseHeaders(
                headerWithName("Location").description("수정이 완료된 리소스 값")
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
                parameterWithName("id").description("인증 ID")
            )
        );
    }
}
