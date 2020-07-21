package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"name"})})
@Builder
@Getter
public class MemberNameUpdateRequest {
    @NotBlank
    private final String name;
}
