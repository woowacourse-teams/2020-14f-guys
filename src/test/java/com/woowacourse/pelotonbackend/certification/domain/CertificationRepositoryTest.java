package com.woowacourse.pelotonbackend.certification.domain;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CertificationRepositoryTest {
    private Certification certification;

    @Autowired
    private CertificationRepository certificationRepository;

    @BeforeEach
    void setUp() {
        certification = createCertificationWithoutId();
    }

    @DisplayName("Certification 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveCertification() {
        final Certification persist = certificationRepository.save(certification);

        assertThat(persist).isEqualToComparingOnlyGivenFields(createCertificationWithoutId(), "status",
            "description", "image", "riderId", "missionId");
    }
}
