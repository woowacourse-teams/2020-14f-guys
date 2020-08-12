package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;
import java.util.Objects;

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
        final JsonNode isEmailValid =
            Objects.nonNull(kakaoAccount.get("is_email_valid")) ? kakaoAccount.get("is_email_valid") :
                kakaoAccount.get("email_valid");
        final JsonNode isEmailVerified =
            Objects.nonNull(kakaoAccount.get("is_email_verified")) ? kakaoAccount.get("is_email_valid") :
                kakaoAccount.get("email_verified");
        final String profileImage =
            Objects.nonNull(properties.get("profile_image")) ? properties.get("profile_image").textValue() :
                null;
        final String thumbnailImage =
            Objects.nonNull(properties.get("thumbnail_image")) ? properties.get("thumbnail_image").textValue() :
                null;

        // TODO: 2020/08/12 Test에서 Serialize를 쓰는데, 그 과정에서 Getter 이름과 필드명 충돌로 인해 임시로 추가해놓음.

        return createKakaoUserResponse(jsonNode, properties, kakaoAccount, isEmailValid, isEmailVerified, profileImage, thumbnailImage);
    }

    private KakaoUserResponse createKakaoUserResponse(final JsonNode jsonNode, final JsonNode properties,
        final JsonNode kakaoAccount, final JsonNode isEmailValid, final JsonNode isEmailVerified,
        final String profileImage, final String thumbnailImage) {
        return KakaoUserResponse.builder()
            .id(jsonNode.get("id").longValue())
            .nickname(properties.get("nickname").textValue())
            .profileImage(profileImage)
            .thumbnailImage(thumbnailImage)
            .hasEmail(kakaoAccount.get("has_email").booleanValue())
            .emailValid(isEmailValid.booleanValue())
            .emailVerified(isEmailVerified.booleanValue())
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
