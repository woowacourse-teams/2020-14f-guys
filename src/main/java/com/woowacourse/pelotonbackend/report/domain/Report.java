package com.woowacourse.pelotonbackend.report.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@Builder
@EqualsAndHashCode(of = "id")
@Getter
public class Report {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotNull
    private final ReportType reportType;

    @NotBlank
    private final String description;

    private final AggregateReference<Certification, @NotNull Long> certificationId;

    private final AggregateReference<Member, @NotNull Long> memberId;

    @CreatedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime createdAt;

    @LastModifiedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime updatedAt;
}
