package com.woowacourse.pelotonbackend.certification.domain;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
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

    @Autowired
    private MissionRepository missionRepository;

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

    @DisplayName("Rider Id로 인증사진을 조회한다."
        + "총 갯수 2개, 페이지 번호 0번, 페이지당 컨텐츠 1개")
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

    @DisplayName("Mission Ids로 인증사진을 조회한다.")
    @Test
    void findByMissionId() {
        final Certification certification = createCertificationWithoutId();
        final Mission mission = MissionFixture.createWithoutId();

        missionRepository.save(mission);
        certificationRepository.save(certification);
        final Page<Certification> certifications = certificationRepository.findByMissionIds(
            Arrays.asList(TEST_MISSION_ID), PageRequest.of(0, 1));

        assertAll(
            () -> assertThat(certifications.getTotalPages()).isEqualTo(1),
            () -> assertThat(certifications.getContent()).hasSize(1),
            () -> assertThat(certifications.getContent().get(0))
                .isEqualToIgnoringGivenFields(certification, "id")
        );
    }

    @DisplayName("라이더 id와 미션 id로 Rider가 존재하는 지 확인한다.")
    @Test
    void existsByRiderIdAndMissionId() {
        final Certification certification = createCertificationWithoutId();
        certificationRepository.save(certification);

        assertThat(certificationRepository.existsByRiderIdAndMissionId(certification.getRiderId().getId(),
            certification.getMissionId().getId())).isTrue();
    }

    @DisplayName("라이더 id와 미션 id로 존재하지 않는 Rider가 존재하지 않는 지 확인한다.")
    @Test
    void notExistsByRiderIdAndMissionId() {
        final Certification certification = createCertificationWithoutId();

        assertThat(certificationRepository.existsByRiderIdAndMissionId(certification.getRiderId().getId(),
            certification.getMissionId().getId())).isFalse();
    }

    @DisplayName("라이더 id와 미션 id로 중복된 라이더를 조회 시 예외를 반환한다.")
    @Test
    void existsDuplicateByRiderIdAndMissionId() {
        final Certification certification = createCertificationWithoutId();
        final Long riderId = certification.getRiderId().getId();
        final Long missionId = certification.getMissionId().getId();
        certificationRepository.save(certification);
        certificationRepository.save(certification);

        assertThatThrownBy(() -> certificationRepository.existsByRiderIdAndMissionId(riderId, missionId))
            .isInstanceOf(AssertionError.class)
            .hasMessage(String.format("There should not be duplicated (rider_id, mission_id), but (%d, %d)", riderId,
                missionId));
    }
}
