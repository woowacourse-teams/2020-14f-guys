package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("cash"))
@Builder
@Getter
public class MemberCashUpdateRequest {
    @Valid @NotNull
    private final Cash cash;
}
