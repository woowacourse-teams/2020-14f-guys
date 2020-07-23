package com.woowacourse.pelotonbackend.common;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"status", "code", "message"}))
@Builder
@Getter
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
    private List<FieldError> errors;

    public static ErrorResponse of(int status, String code, String message, BindingResult result) {
        return new ErrorResponse(status, code, message, FieldError.of(result));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private static List<FieldError> of(BindingResult result) {
            return result.getFieldErrors().stream()
                .map(field -> new FieldError(
                    field.getField(),
                    field.getRejectedValue() == null ? "" : field.getRejectedValue().toString(),
                    field.getDefaultMessage()))
                .collect(Collectors.toList());
        }
    }
}
