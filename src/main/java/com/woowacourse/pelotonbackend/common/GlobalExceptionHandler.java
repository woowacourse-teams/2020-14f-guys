package com.woowacourse.pelotonbackend.common;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.woowacourse.pelotonbackend.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ErrorResponse> validException(final MethodArgumentNotValidException exception) {
        log.error("Validate Exception ! ", exception);

        final ErrorCode errorCode = ErrorCode.INVALID_VALIDATE;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(),
            "Invalid Validation", exception.getBindingResult());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> bindException(final BindException exception) {
        log.error("HandleBind Exception ! ", exception);

        final ErrorCode errorCode = ErrorCode.INVALID_VALIDATE;
        final ErrorResponse errorResponse = ErrorResponse.of(
            errorCode.getStatus(),
            errorCode.getCode(),
            "Invalid Binding",
            exception.getBindingResult());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler({ConstraintViolationException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<ErrorResponse> invalidInputException(final RuntimeException exception) {
        log.error(exception.getClass().getSimpleName() + " Exception !", exception);

        final ErrorCode errorCode = ErrorCode.INVALID_VALIDATE;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(),
            exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> businessException(final BusinessException exception) {
        log.error("Business Exception ! ", exception);

        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.of(
            errorCode.getStatus(),
            errorCode.getCode(),
            exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> UnexpectedException(final Exception exception) {
        log.error("Unexpected Exception ! ", exception);

        final ErrorResponse errorResponse = ErrorResponse.of(
            ErrorCode.UNEXPECTED.getStatus(),
            ErrorCode.UNEXPECTED.getCode(),
            exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ErrorCode.UNEXPECTED.getStatus()));
    }
}
