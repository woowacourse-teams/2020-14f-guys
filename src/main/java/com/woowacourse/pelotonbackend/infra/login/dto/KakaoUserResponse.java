package com.woowacourse.pelotonbackend.infra.login.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.support.jsonparser.KakaoUserResponseDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.KakaoUserResponseSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
@JsonDeserialize(using = KakaoUserResponseDeserializer.class)
@JsonSerialize(using = KakaoUserResponseSerializer.class)
public class KakaoUserResponse {
    private final long id;
    private final String nickname;
    private final String profileImage;
    private final String thumbnailImage;
    private final boolean hasEmail;
    private final boolean emailValid;
    private final boolean emailVerified;
    private final String email;
    private final boolean emailNeedsAgreement;
    private final boolean hasBirthday;
    private final boolean birthdayNeedsAgreement;
    private final String birthday;
    private final boolean hasGender;
    private final boolean genderNeedsAgreement;
}

