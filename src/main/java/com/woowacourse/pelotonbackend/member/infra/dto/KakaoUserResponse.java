package com.woowacourse.pelotonbackend.member.infra.dto;

import java.beans.ConstructorProperties;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"id", "nickname",
    "profileImage", "thumbnailImage", "hasEmail", "isEmailValid", "isEmailVerified", "email", "emailNeedsAgreement",
    "hasBirthday", "birthdayNeedsAgreement", "birthday", "hasGender", "genderNeedsAgreement"}))
@Builder
@Getter
public class KakaoUserResponse {
    private final long id;
    private String nickname;
    private String profileImage;
    private String thumbnailImage;
    private boolean hasEmail;
    private boolean isEmailValid;
    private boolean isEmailVerified;
    private String email;
    private boolean emailNeedsAgreement;
    private boolean hasBirthday;
    private boolean birthdayNeedsAgreement;
    private String birthday;
    private boolean hasGender;
    private boolean genderNeedsAgreement;

    @JsonProperty("properties")
    public void unpackProperties(Map<String, String> properties) {
        this.nickname = properties.get("nickname");
        this.profileImage = properties.get("profile_image");
        this.thumbnailImage = properties.get("thumbnail_image");
    }

    @JsonProperty("kakao_account")
    @JsonIgnoreProperties({"profile"})
    public void unpackKakaoAccount(Map<String, String> kakaoAccount) {
        this.hasEmail = Boolean.parseBoolean(kakaoAccount.get("has_email"));
        this.isEmailValid = Boolean.parseBoolean(kakaoAccount.get("is_email_valid"));
        this.isEmailVerified = Boolean.parseBoolean(kakaoAccount.get("is_email_verified"));
        this.email = kakaoAccount.get("email");
        this.emailNeedsAgreement = Boolean.parseBoolean(kakaoAccount.get("email_needs_agreement"));
        this.hasBirthday = Boolean.parseBoolean(kakaoAccount.get("has_birthday"));
        this.birthdayNeedsAgreement = Boolean.parseBoolean(kakaoAccount.get("birthday_needs_agreement"));
        this.birthday = kakaoAccount.get("birthday");
        this.hasGender = Boolean.parseBoolean(kakaoAccount.get("has_gender"));
        this.genderNeedsAgreement = Boolean.parseBoolean(kakaoAccount.get("gender_needs_agreement"));
    }
}

