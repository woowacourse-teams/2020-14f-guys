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

        return createKakaoUserResponse(jsonNode, properties, kakaoAccount, isEmailValid, isEmailVerified, profileImage,
            thumbnailImage);
    }

    private KakaoUserResponse createKakaoUserResponse(final JsonNode jsonNode, final JsonNode properties,
        final JsonNode kakaoAccount, final JsonNode isEmailValid, final JsonNode isEmailVerified,
        final String profileImage, final String thumbnailImage) {
        return KakaoUserResponse.builder()
            .id(jsonNode.get("id").longValue())
            .nickname(properties.get("nickname").textValue())
            .profileImage(profileImage)
            .thumbnailImage(thumbnailImage)
            .hasEmail(ifNullReturnFalse(kakaoAccount, "has_email"))
            .emailValid(!Objects.isNull(isEmailValid) && isEmailValid.booleanValue())
            .emailVerified(!Objects.isNull(isEmailVerified) && isEmailVerified.booleanValue())
            .email(Objects.isNull(kakaoAccount.get("email")) ? null : kakaoAccount.get("email").textValue())
            .emailNeedsAgreement(ifNullReturnTrue(kakaoAccount, "email_needs_agreement"))
            .hasBirthday(ifNullReturnFalse(kakaoAccount, "has_birthday"))
            .birthdayNeedsAgreement(ifNullReturnTrue(kakaoAccount, "birthday_needs_agreement"))
            .birthday(Objects.isNull(kakaoAccount.get("birthday")) ? null : kakaoAccount.get("birthday").textValue())
            .hasGender(ifNullReturnFalse(kakaoAccount, "has_gender"))
            .genderNeedsAgreement(
                ifNullReturnTrue(kakaoAccount, "gender_needs_agreement")).build();
    }

    private boolean ifNullReturnFalse(final JsonNode kakaoAccount, final String has_birthday) {
        return !Objects.isNull(kakaoAccount.get(has_birthday)) && kakaoAccount.get(has_birthday).booleanValue();
    }

    private boolean ifNullReturnTrue(final JsonNode kakaoAccount, final String email_needs_agreement) {
        return Objects.isNull(kakaoAccount.get(email_needs_agreement)) || kakaoAccount.get(email_needs_agreement)
            .booleanValue();
    }
}
