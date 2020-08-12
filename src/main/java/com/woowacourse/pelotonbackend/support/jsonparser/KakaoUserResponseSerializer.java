package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoUserResponse;
import lombok.Builder;
import lombok.Getter;

public class KakaoUserResponseSerializer extends StdSerializer<KakaoUserResponse> {
    protected KakaoUserResponseSerializer() {
        this(null);
    }

    protected KakaoUserResponseSerializer(final Class<KakaoUserResponse> t) {
        super(t);
    }

    @Override
    public void serialize(final KakaoUserResponse value, final JsonGenerator gen,
        final SerializerProvider serializers) throws IOException {

        final Properties properties = Properties.of(value);
        final KakaoAccount kakaoAccount = KakaoAccount.of(value);

        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeObjectField("properties", properties);
        gen.writeObjectField("kakao_account", kakaoAccount);
        gen.writeEndObject();
    }

    @Builder
    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    private static class Properties {
        private final String nickname;
        private final String profileImage;
        private final String thumbnailImage;

        private static Properties of(final KakaoUserResponse response) {
            return Properties.builder()
                .nickname(response.getNickname())
                .profileImage(response.getProfileImage())
                .thumbnailImage(response.getThumbnailImage())
                .build();
        }
    }

    @Builder
    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    private static class KakaoAccount {
        private final boolean hasEmail;
        private final boolean isEmailValid;
        private final boolean isEmailVerified;
        private final String email;
        private final boolean emailNeedsAgreement;
        private final boolean hasBirthday;
        private final boolean birthdayNeedsAgreement;
        private final String birthday;
        private final boolean hasGender;
        private final boolean genderNeedsAgreement;

        private static KakaoAccount of(final KakaoUserResponse response) {
            return KakaoAccount.builder()
                .hasEmail(response.isHasEmail())
                .isEmailValid(response.isEmailValid())
                .isEmailVerified(response.isEmailVerified())
                .email(response.getEmail())
                .emailNeedsAgreement(response.isEmailNeedsAgreement())
                .hasBirthday(response.isHasBirthday())
                .birthdayNeedsAgreement(response.isBirthdayNeedsAgreement())
                .birthday(response.getBirthday())
                .hasGender(response.isHasGender())
                .genderNeedsAgreement(response.isGenderNeedsAgreement())
                .build();
        }
    }
}
