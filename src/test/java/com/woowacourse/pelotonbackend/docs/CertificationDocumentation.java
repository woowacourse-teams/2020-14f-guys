package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
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
                parameterWithName("description").description("인증 세부내용"),
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
                parameterWithName("description").description("인증 세부내용"),
                parameterWithName("riderId").description("인증 라이더 ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            getErrorResponseFieldsWithFieldErrors()
        );
    }

}
