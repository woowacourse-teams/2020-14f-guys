package com.woowacourse.pelotonbackend.certification.presentation;

import java.beans.ConstructorProperties;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"id", "image", "status",
    "missionId", "riderId", "description"}))
@Builder
@Getter
public class CertificationResponse {
    private final Long id;
    private final ImageUrl image;
    private final CertificationStatus status;
    private final String description;
    private final Long missionId;
    private final Long riderId;

    public static CertificationResponse of(final Certification certification) {
        return CertificationResponse.builder()
            .id(certification.getId())
            .image(certification.getImage())
            .missionId(certification.getMissionId().getId())
            .riderId(certification.getRiderId().getId())
            .status(certification.getStatus())
            .description(certification.getDescription())
            .build();
    }
}
