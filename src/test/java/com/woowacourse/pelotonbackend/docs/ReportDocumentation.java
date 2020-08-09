package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static com.woowacourse.pelotonbackend.docs.DocumentFormatGenerator.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.apache.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class ReportDocumentation {
    public static RestDocumentationResultHandler createReport() {
        return document("report/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("OAuth2 토큰 헤더")
            ),
            requestFields(
                fieldWithPath("reportType").type(STRING).attributes(getReportTypeFormat()).description("Report 종류"),
                fieldWithPath("description").type(STRING).description("Report 상세내용"),
                fieldWithPath("reportMemberId").type(NUMBER).description("신고하는 Member ID"),
                fieldWithPath("certificationId").type(NUMBER).description("신고하는 Certification ID")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }
}
