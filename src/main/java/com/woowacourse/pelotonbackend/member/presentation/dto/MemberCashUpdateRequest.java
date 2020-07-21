package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"cash"})})
@Builder
@Getter
public class MemberCashUpdateRequest {
    private final Cash cash;
}
