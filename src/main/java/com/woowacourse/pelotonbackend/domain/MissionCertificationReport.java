package com.woowacourse.pelotonbackend.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

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
public class MissionCertificationReport {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotNull
    private final ReportType reportType;

    @NotBlank
    private final String description;

    @NotNull
    private final AggregateReference<MissionCertification, @NotNull Long> missionCertificationId;

    @NotNull
    private final AggregateReference<Member, @NotNull Long> memberId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
