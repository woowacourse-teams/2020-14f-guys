package com.woowacourse.pelotonbackend.missioncertification;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;

import com.woowacourse.pelotonbackend.vo.ImageUrl;
import com.woowacourse.pelotonbackend.rider.Rider;
import com.woowacourse.pelotonbackend.mission.Mission;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@EqualsAndHashCode(of = "id")
@Getter
public class MissionCertification {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotNull
    private final MissionCertificationStatus status;

    @NotBlank
    private final String description;

    @Embedded.Empty
    private final ImageUrl image;

    @NotNull
    private final AggregateReference<Rider, @NotNull Long> riderId;

    @NotNull
    private final AggregateReference<Mission, @NotNull Long> missionId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
