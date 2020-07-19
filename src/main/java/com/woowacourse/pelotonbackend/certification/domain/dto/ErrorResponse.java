package com.woowacourse.pelotonbackend.certification.domain.dto;

import java.beans.ConstructorProperties;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"message", "status", "errors", "code"}))
@Getter
public class ErrorResponse {
    private final String message;
    private final int status;
    private final List<FieldError> errors;

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return new ErrorResponse(errorCode.getMessage(), errorCode.getStatusCode(), bindingResult.getFieldErrors());
    }
}
