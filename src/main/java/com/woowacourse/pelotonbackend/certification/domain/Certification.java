package com.woowacourse.pelotonbackend.certification.domain;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;

import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationDescriptionUpdateRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationStatusUpdateRequest;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Getter
public class Certification {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotNull
    private final CertificationStatus status;

    private final String description;

    @Embedded.Empty @Valid
    private final ImageUrl image;

    private final AggregateReference<Rider, @NotNull Long> riderId;

    private final AggregateReference<Mission, @NotNull Long> missionId;

    @CreatedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime createdAt;

    @LastModifiedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime updatedAt;

    public Certification updateDescription(final CertificationDescriptionUpdateRequest request) {
        return this.toBuilder()
            .description(request.getDescription())
            .build();
    }

    public Certification updateStatus(final CertificationStatusUpdateRequest request) {
        return this.toBuilder()
            .status(request.getStatus())
            .build();
    }
}
