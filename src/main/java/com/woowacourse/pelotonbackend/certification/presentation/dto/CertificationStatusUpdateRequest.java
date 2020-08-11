package com.woowacourse.pelotonbackend.certification.presentation.dto;

import java.beans.ConstructorProperties;

import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("cash"))
@Builder
@Getter
public class CertificationStatusUpdateRequest {
    private final CertificationStatus status;
}
