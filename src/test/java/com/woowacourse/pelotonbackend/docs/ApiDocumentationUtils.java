package com.woowacourse.pelotonbackend.docs;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public interface ApiDocumentationUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
            modifyUris()
                .scheme("https")
                .host("peloton.ga")
                .removePort(),
            prettyPrint()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    static ResponseFieldsSnippet getErrorResponseFieldsWithFieldErrors() {
        return responseFields(
            fieldWithPath("status").description("에러 상태"),
            fieldWithPath("code").description("에러 코드"),
            fieldWithPath("message").description("에러 메세지"),
            subsectionWithPath("errors").description("필드 에러").optional()
        );
    }

    static ResponseFieldsSnippet getErrorResponseFields() {
        return responseFields(
            fieldWithPath("status").description("에러 상태"),
            fieldWithPath("code").description("에러 코드"),
            fieldWithPath("message").description("에러 메세지")
        );
    }
}
