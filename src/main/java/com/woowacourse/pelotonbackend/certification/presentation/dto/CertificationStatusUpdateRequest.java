package com.woowacourse.pelotonbackend.certification.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("status"))
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CertificationStatusUpdateRequest {
    @NotNull
    private final CertificationStatus status;

    public Certification getUpdatedCertification(final Certification certification) {
        return certification.toBuilder()
            .status(status)
            .build();
    }
}
