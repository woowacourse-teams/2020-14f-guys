package com.woowacourse.pelotonbackend.rider.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.race.domain.Race;
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
public class Rider {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    private final AggregateReference<Member, @NotNull Long> memberId;

    private final AggregateReference<Race, @NotNull Long> raceId;

    @CreatedDate @PastOrPresent
    private LocalDateTime createdAt;

    @LastModifiedDate @PastOrPresent
    private LocalDateTime updatedAt;
}
