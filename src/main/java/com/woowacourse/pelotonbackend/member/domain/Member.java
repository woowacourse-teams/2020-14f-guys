package com.woowacourse.pelotonbackend.member.domain;

import java.math.BigDecimal;
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

import com.woowacourse.pelotonbackend.common.exception.MoneyInvalidException;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;
import org.springframework.data.relational.core.mapping.Table;

@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Getter
@Table
public class Member {
    public static final Member UNREGISTERED = Member.builder().id(0L).name("탈퇴 회원").build();

    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotNull
    private final Long kakaoId;

    @NotNull @Valid
    @Embedded(prefix = "PROFILE_", onEmpty = Embedded.OnEmpty.USE_EMPTY)
    private final ImageUrl profile;

    @NotBlank
    private final String name;

    @Email
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

    public Member changeName(final String name) {
        return this.toBuilder()
            .name(name)
            .build();
    }

    public Member plusCash(final Cash value) {
        return this.toBuilder()
            .cash(cash.plus(value))
            .build();
    }

    public Member minusCash(final Cash value) {
        if (isNotEnoughMoney(value)) {
            throw new MoneyInvalidException();
        }

        return this.toBuilder()
            .cash(cash.minus(value))
            .build();
    }

    private boolean isNotEnoughMoney(final Cash value) {
        final Cash calculatedMoney = cash.minus(value);
        return !calculatedMoney.isGreaterOrEqualThan(BigDecimal.ZERO);
    }

    public Member changeProfile(final ImageUrl profileImageUrl) {
        return this.toBuilder()
            .profile(profileImageUrl)
            .build();
    }
}
