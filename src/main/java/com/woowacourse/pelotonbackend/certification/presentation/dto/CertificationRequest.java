package com.woowacourse.pelotonbackend.certification.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotNull;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"status", "description", "riderId", "missionId"}))
@Builder
@Getter
public class CertificationRequest {
    @NotNull
    private final CertificationStatus status;

    private final String description;

    @NotNull
    private final Long riderId;

    @NotNull
    private final Long missionId;

    public Certification toCertification(final String imageUrl) {
        return Certification.builder()
            .status(status)
            .description(description)
            .riderId(AggregateReference.to(riderId))
            .missionId(AggregateReference.to(missionId))
            .image(new ImageUrl(imageUrl))
            .build();
    }

    public Certification toUpdatedCertification(final Certification certification, final String imageUrl) {
        return certification.toBuilder()
            .status(status)
            .description(description)
            .image(new ImageUrl(imageUrl))
            .missionId(AggregateReference.to(missionId))
            .riderId(AggregateReference.to(riderId))
            .build();
    }
}
