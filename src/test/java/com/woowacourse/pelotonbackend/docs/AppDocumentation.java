package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class AppDocumentation {
    public static RestDocumentationResultHandler redirect() {
        return document("app/redirect",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("raceId").description("참여하고자 하는 레이스 아이디")
            ),
            responseHeaders(
                headerWithName("Location").description("redirect uri")
            )
        );
    }
}
