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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

public class CalculationDocumentation {

    public static RestDocumentationResultHandler create() {
        return document("calculation/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 토큰")
            ),
            pathParameters(
                parameterWithName("raceId").description("정산하고자 하는 레이스의 아이디"),
                parameterWithName("riderId").description("정산받고자 하는 라이더의 아이디")
            ),
            responseHeaders(
                headerWithName("Location").description("생성된 정산의 리소스")
            )
        );
    }

    public static RestDocumentationResultHandler retrieve() {
        return document("calculation/get-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 토큰")
            ),
            pathParameters(
                parameterWithName("raceId").description("정산하고자 하는 레이스의 아이디"),
                parameterWithName("riderId").description("정산받고자 하는 라이더의 아이디")
            ),
            responseFields(
                fieldWithPath("calculationResponses").type(ARRAY).description("정산 결과"),
                fieldWithPath("calculationResponses[].rider_id").type(NUMBER).description("정산받은 라이더의 아이디"),
                fieldWithPath("calculationResponses[].race_id").type(NUMBER).description("정산된 레이스의 아이디"),
                fieldWithPath("calculationResponses[].prize").type(STRING).description("각 라이더별 정산 금액"),
                fieldWithPath("calculationResponses[].created_at").type(STRING).attributes(getDateTimeFormat()).description("정산 날짜"),
                fieldWithPath("calculationResponses[].calculated").type(BOOLEAN).description("정산 여부")
            )
        );
    }
}
