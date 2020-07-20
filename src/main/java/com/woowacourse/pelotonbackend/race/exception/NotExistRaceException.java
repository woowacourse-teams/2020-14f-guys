package com.woowacourse.pelotonbackend.race.exception;

public class NotExistRaceException extends RuntimeException {
    public NotExistRaceException(final Long id) {
        super(String.format("Race(id: %d) is not exists", id));
    }
}
