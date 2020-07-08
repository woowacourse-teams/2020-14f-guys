package com.woowacourse.pelotonbackend.domain;

import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@EqualsAndHashCode(of = "id")
@Getter
public class Member {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    private final String name;
}
