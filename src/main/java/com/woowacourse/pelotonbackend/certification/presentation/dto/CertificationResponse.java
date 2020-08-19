package com.woowacourse.pelotonbackend.certification.presentation.dto;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"id", "image", "status","description",
    "missionId", "riderId", "created_at"}))
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CertificationResponse {
    private final Long id;
    private final ImageUrl image;
    private final CertificationStatus status;
    private final String description;
    private final Long missionId;
    private final Long riderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime createdAt;

    public static CertificationResponse of(final Certification certification) {
        return CertificationResponse.builder()
            .id(certification.getId())
            .image(certification.getImage())
            .missionId(certification.getMissionId().getId())
            .riderId(certification.getRiderId().getId())
            .status(certification.getStatus())
            .description(certification.getDescription())
            .createdAt(certification.getCreatedAt())
            .build();
    }
}
