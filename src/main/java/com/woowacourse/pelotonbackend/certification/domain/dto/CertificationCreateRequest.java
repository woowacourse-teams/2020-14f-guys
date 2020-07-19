package com.woowacourse.pelotonbackend.certification.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CertificationCreateRequest {
    @NotNull
    private final CertificationStatus status;

    @NotBlank
    private final String description;

    @NotNull
    private final Long riderId;

    @NotNull
    private final Long missionId;

    public Certification toEntity(final String imageUrl) {
        return Certification.builder()
            .status(this.status)
            .description(this.description)
            .riderId(AggregateReference.to(this.riderId))
            .missionId(AggregateReference.to(this.missionId))
            .image(new ImageUrl(imageUrl))
            .build();
    }
}
