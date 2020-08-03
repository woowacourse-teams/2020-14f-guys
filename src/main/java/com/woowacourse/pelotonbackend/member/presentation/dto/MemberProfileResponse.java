package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties("imageUrl")})
@Getter
public class MemberProfileResponse {
    private final String imageUrl;
}
