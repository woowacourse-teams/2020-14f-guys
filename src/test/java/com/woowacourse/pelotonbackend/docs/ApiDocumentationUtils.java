package com.woowacourse.pelotonbackend.docs;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

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
}
