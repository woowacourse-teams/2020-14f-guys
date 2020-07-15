package com.woowacourse.pelotonbackend.certification.domain.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotNull;

import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"status, encodedImage, description"})})
@Getter
public class CertificationRequest {
    @NotNull
    private final CertificationStatus status;

    @NotNull
    private final String description;
}
