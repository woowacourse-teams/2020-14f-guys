package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("name"))
@Builder
@Getter
public class MemberNameUpdateRequest {
    @NotBlank
    private final String name;
}
