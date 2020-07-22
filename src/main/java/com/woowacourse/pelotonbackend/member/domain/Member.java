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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@Builder
@EqualsAndHashCode(of = "id")
@Getter
public class Member {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotBlank
    private final String name;

    @NotBlank @Email
    private final String email;

    @Embedded.Empty @Valid
    private final Cash cash;

    @NotNull
    private final Role role;

    @CreatedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime createdAt;

    @LastModifiedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime updatedAt;

    public Member update(final String name) {
        return Member.builder()
            .id(this.id)
            .email(this.email)
            .cash(this.cash)
            .role(this.role)
            .name(name)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public Member update(final Cash cash) {
        return Member.builder()
            .id(this.id)
            .email(this.email)
            .cash(cash)
            .role(this.role)
            .name(this.name)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
