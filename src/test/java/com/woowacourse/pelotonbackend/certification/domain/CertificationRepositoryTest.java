package com.woowacourse.pelotonbackend.certification.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.vo.ImageUrl;

@SpringBootTest
class CertificationRepositoryTest {
    @Autowired
    private CertificationRepository certificationRepository;

    @DisplayName("Certification 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveCertification() {
        final Certification certification = Certification.builder()
            .description("오늘 하루도 화이팅")
            .image(new ImageUrl("https://abc.com"))
            .status(CertificationStatus.SUCCESS)
            .riderId(AggregateReference.to(1L))
            .missionId(AggregateReference.to(1L))
            .build();

        final Certification persist = certificationRepository.save(certification);

        assertAll(
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getDescription()).isEqualTo("오늘 하루도 화이팅"),
            () -> assertThat(persist.getImage()).isEqualTo(new ImageUrl("https://abc.com")),
            () -> assertThat(persist.getStatus()).isEqualTo(CertificationStatus.SUCCESS),
            () -> assertThat(persist.getRiderId()).isEqualTo(AggregateReference.to(1L)),
            () -> assertThat(persist.getMissionId()).isEqualTo(AggregateReference.to(1L)),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }
}
