package com.woowacourse.pelotonbackend.member.domain;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Embedded;

import com.woowacourse.pelotonbackend.vo.Cash;
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
public class Member {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotBlank
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @Embedded.Empty
    @Valid
    private final Cash cash;

    @NotNull
    private final Role role;

    @CreatedDate
    @PastOrPresent
    private LocalDateTime createdAt;

    @LastModifiedDate
    @PastOrPresent
    private LocalDateTime updatedAt;
}
