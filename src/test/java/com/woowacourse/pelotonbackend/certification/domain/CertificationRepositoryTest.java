package com.woowacourse.pelotonbackend.certification.domain;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;

@SpringBootTest
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
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

    @DisplayName("Rider Id로 인증사진을 조회한다. 총 갯수 2개, 페이지 번호 0번, 페이지당 컨텐츠 1개")
    @Test
    void findByRiderId() {
        certificationRepository.save(CertificationFixture.createCertificationWithoutId());
        certificationRepository.save(CertificationFixture.createCertificationWithoutId());
        final Page<Certification> certifications = certificationRepository.findByRiderId(TEST_RIDER_ID,
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
        certificationRepository.save(certification);
        final Page<Certification> certifications = certificationRepository.findByMissionIdsAndStatus(
            Arrays.asList(TEST_MISSION_ID), CertificationStatus.SUCCESS, PageRequest.of(0, 1));

        assertAll(
            () -> assertThat(certifications.getTotalPages()).isEqualTo(1),
            () -> assertThat(certifications.getContent()).hasSize(1),
            () -> assertThat(certifications.getContent().get(0))
                .isEqualToIgnoringGivenFields(certification, "id", "createdAt", "updatedAt")
        );
    }

    @Test
    void findByMissionIdsAndRiderIds() {
        final Certification certification1 = createCertificationWithoutId();
        final Certification certification2 = updatedCertification().toBuilder().id(null).build();

        certificationRepository.saveAll(Arrays.asList(certification1, certification1, certification2));

        final Page<Certification> founded = certificationRepository.findByMissionIdsAndRiderIds(
            Arrays.asList(TEST_MISSION_ID, TEST_UPDATED_MISSION_ID),
            Collections.singletonList(TEST_RIDER_ID), PageRequest.of(0, Integer.MAX_VALUE));

        assertThat(founded.getContent()).usingElementComparatorIgnoringFields("id", "createdAt", "updatedAt")
            .isEqualTo(Lists.newArrayList(certification1, certification1));
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

    @DisplayName("unpaged를 사용할 때, 페이지 1에 미션에 모두 담기는지 확인한다.")
    @Test
    void findByMissionIdsUnPaged() {
        final Certification certification = createCertificationWithoutId();
        final Long missionId = certification.getMissionId().getId();
        final List<Certification> certifications =
            Arrays.asList(certification, certification, certification, certification, certification);
        certificationRepository.saveAll(certifications);

        final Page<Certification> result = certificationRepository.findByMissionIdsAndStatus(
            Collections.singletonList(missionId), CertificationStatus.SUCCESS, PageRequest.of(0, Integer.MAX_VALUE));
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(certifications.size());
        assertThat(result.getContent())
            .usingRecursiveFieldByFieldElementComparator()
            .usingElementComparatorIgnoringFields("id", "createdAt", "updatedAt")
            .isEqualTo(certifications);
    }

    @DisplayName("MissionIds와 CertificationStatus로 잘 가져오는 지 확인한다.")
    @Test
    void findByMissionIdsAndStatus() {
        final Certification certification = createCertificationWithoutId();
        final Long missionId = certification.getMissionId().getId();
        final List<Certification> certifications =
            Arrays.asList(certification, certification, certification, certification, certification);
        certificationRepository.saveAll(certifications);

        final Page<Certification> result = certificationRepository.findByMissionIdsAndStatus(
            Collections.singletonList(missionId), CertificationStatus.SUCCESS, PageRequest.of(0, Integer.MAX_VALUE));
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(certifications.size());
        assertThat(result.getContent())
            .usingRecursiveFieldByFieldElementComparator()
            .usingElementComparatorIgnoringFields("id", "createdAt", "updatedAt")
            .isEqualTo(certifications);
    }

    @DisplayName("MissionIds와 CertificationStatus로 잘 가져오는 지 확인한다."
        + "모두 성공한 인증 5개, 실패한 인증 찾기 : 0개")
    @Test
    void findByMissionIdsAndStatusFail() {
        final Certification certification = createCertificationWithoutId();
        final Long missionId = certification.getMissionId().getId();
        final List<Certification> certifications =
            Arrays.asList(certification, certification, certification, certification, certification);
        certificationRepository.saveAll(certifications);

        final Page<Certification> result = certificationRepository.findByMissionIdsAndStatus(
            Collections.singletonList(missionId), CertificationStatus.FAIL, PageRequest.of(0, Integer.MAX_VALUE));
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(0);
    }

    @DisplayName("MissionIds와 CertificationStatus로 잘 가져오는 지 확인한다."
        + "모두 성공한 인증 5개, 신고 받은 인증을 찾기 : 0개")
    @Test
    void findByMissionIdsAndStatusReported() {
        final Certification certification = createCertificationWithoutId();
        final Long missionId = certification.getMissionId().getId();
        final List<Certification> certifications =
            Arrays.asList(certification, certification, certification, certification, certification);
        certificationRepository.saveAll(certifications);

        final Page<Certification> result = certificationRepository.findByMissionIdsAndStatus(
            Collections.singletonList(missionId), CertificationStatus.REPORTED, PageRequest.of(0, Integer.MAX_VALUE));
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(0);
    }

    @DisplayName("certificationIds가 빈 리스트면 비어있는 결과를 리턴")
    @Test
    void noCertificationReturnEmpty() {
        final Page<Certification> results = certificationRepository.findByMissionIdsAndStatus(
            Collections.emptyList(), CertificationStatus.SUCCESS, PageRequest.of(0, Integer.MAX_VALUE));

        assertThat(results.getTotalPages()).isEqualTo(0);
        assertThat(results.getTotalElements()).isEqualTo(0);
    }

    @DisplayName("certificationIds, riderIds 둘 중 하나라도 빈 리스트면 비어있는 결과를 리턴")
    @ParameterizedTest
    @MethodSource("generateEmptyList")
    void noRiderOrCertificationReturnEmpty(final List<Long> certificationIds, final List<Long> riderIds) {
        final Page<Certification> results = certificationRepository.findByMissionIdsAndRiderIds(
            certificationIds, riderIds, PageRequest.of(0, Integer.MAX_VALUE));

        assertThat(results.getTotalPages()).isEqualTo(0);
        assertThat(results.getTotalElements()).isEqualTo(0);
    }

    private static Stream<Arguments> generateEmptyList() {
        return Stream.of(
            Arguments.of(Collections.emptyList(), Arrays.asList(1L, 2L, 3L)),
            Arguments.of(Arrays.asList(1L, 2L, 3L), Collections.emptyList()),
            Arguments.of(Collections.emptyList(), Collections.emptyList())
        );
    }
}
