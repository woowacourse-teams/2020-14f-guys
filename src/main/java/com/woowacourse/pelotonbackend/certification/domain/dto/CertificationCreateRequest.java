package com.woowacourse.pelotonbackend.certification.domain.dto;

import javax.validation.constraints.NotNull;

import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CertificationCreateRequest {
    @NotNull
    private final CertificationStatus status;

    @NotNull
    private final String description;
}
