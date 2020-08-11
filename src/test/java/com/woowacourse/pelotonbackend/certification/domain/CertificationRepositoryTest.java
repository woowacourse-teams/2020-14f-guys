package com.woowacourse.pelotonbackend.certification.domain;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;

@SpringBootTest
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class CertificationRepositoryTest {
    private Certification certification;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private RiderRepository riderRepository;

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

    @DisplayName("Rider Id로 인증사진을 조회한다.")
    @Test
    void findByRiderId() {
        final Rider rider = riderRepository.save(RiderFixture.createRiderWithoutId());
        certificationRepository.save(CertificationFixture.createCertificationWithoutId());
        certificationRepository.save(CertificationFixture.createCertificationWithoutId());

        final Page<Certification> certifications = certificationRepository.findByRiderId(rider.getId(),
            PageRequest.of(0, 1));

        assertAll(
            () -> assertThat(certifications.getTotalPages()).isEqualTo(2),
            () -> assertThat(certifications.getContent().size()).isEqualTo(1),
            () -> assertThat(certifications.getContent().get(0).getId()).isEqualTo(1L)
        );
    }
}
