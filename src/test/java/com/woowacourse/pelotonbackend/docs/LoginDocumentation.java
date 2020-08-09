package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.apache.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.ResultHandler;

public class LoginDocumentation {
    public static RestDocumentationResultHandler getCode() {
        return document("login/get-code",
            getDocumentRequest(),
            getDocumentResponse(),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler getToken() {
        return document("login/get-token",
            getDocumentRequest(),
            getDocumentResponse(),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler loginCheckSuccess() {
        return document("login/check-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestParameters(
                parameterWithName("access_token").description("액세스 토큰").optional(),
                parameterWithName("success").description("성공 여부"),
                parameterWithName("is_created").description("가입 여부")
            ),
            responseFields(
                fieldWithPath("accessToken").description("액세스 토큰"),
                fieldWithPath("created").description("가입 여부")
            )
        );
    }

    public static RestDocumentationResultHandler loginCheckFail() {
        return document("login/check-fail",
            getDocumentRequest(),
            getDocumentResponse(),
            requestParameters(
                parameterWithName("success").description("성공 여부"),
                parameterWithName("is_created").description("가입 여부")
            )
        );
    }
}
