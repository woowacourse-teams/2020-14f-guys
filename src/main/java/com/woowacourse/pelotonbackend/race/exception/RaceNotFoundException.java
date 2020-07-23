package com.woowacourse.pelotonbackend.race.exception;

public class RaceNotFoundException extends RuntimeException {
    public RaceNotFoundException(final Long id) {
        super(String.format("Race(id: %d) is not exists", id));
    }
}
