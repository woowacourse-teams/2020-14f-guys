package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoUserResponse;

public class KakaoUserResponseDeserializer extends StdDeserializer<KakaoUserResponse> {
    public KakaoUserResponseDeserializer() {
        this(null);
    }

    public KakaoUserResponseDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public KakaoUserResponse deserialize(final JsonParser p, final DeserializationContext ctxt) throws
        IOException {

        final JsonNode jsonNode = p.getCodec().readTree(p);

        final JsonNode properties = jsonNode.get("properties");
        final JsonNode kakaoAccount = jsonNode.get("kakao_account");

        return KakaoUserResponse.builder()
            .id(jsonNode.get("id").longValue())
            .nickname(properties.get("nickname").textValue())
            .profileImage(properties.get("profile_image").textValue())
            .thumbnailImage(properties.get("thumbnail_image").textValue())
            .hasEmail(kakaoAccount.get("has_email").booleanValue())
            .emailValid(kakaoAccount.get("email_valid").booleanValue())
            .emailVerified(kakaoAccount.get("email_verified").booleanValue())
            .email(kakaoAccount.get("email").textValue())
            .emailNeedsAgreement(kakaoAccount.get("email_needs_agreement").booleanValue())
            .hasBirthday(kakaoAccount.get("has_birthday").booleanValue())
            .birthdayNeedsAgreement(kakaoAccount.get("birthday_needs_agreement").booleanValue())
            .birthday(kakaoAccount.get("birthday").textValue())
            .hasGender(kakaoAccount.get("has_gender").booleanValue())
            .genderNeedsAgreement(kakaoAccount.get("gender_needs_agreement").booleanValue())
            .build();
    }
}
