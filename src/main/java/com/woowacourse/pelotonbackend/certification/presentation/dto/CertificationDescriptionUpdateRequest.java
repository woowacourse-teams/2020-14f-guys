package com.woowacourse.pelotonbackend.certification.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("cash"))
@Builder
@Getter
public class CertificationDescriptionUpdateRequest {
    @NotBlank
    private final String description;
}
