package com.woowacourse.pelotonbackend.race.presentation.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_EXIT_RACE(HttpStatus.NOT_FOUND, "Race가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
