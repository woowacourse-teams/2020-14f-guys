package com.woowacourse.pelotonbackend.missioncertificationreport.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.missioncertification.domain.MissionCertification;
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

    private final AggregateReference<MissionCertification, @NotNull Long> missionCertificationId;

    private final AggregateReference<Member, @NotNull Long> memberId;

    @CreatedDate
    @PastOrPresent
    private LocalDateTime createdAt;

    @LastModifiedDate
    @PastOrPresent
    private LocalDateTime updatedAt;
}
