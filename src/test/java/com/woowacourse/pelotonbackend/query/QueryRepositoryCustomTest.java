package com.woowacourse.pelotonbackend.query;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;

@SpringBootTest
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class QueryRepositoryCustomTest {
    @Autowired
    private QueryRepositoryCustom queryRepositoryCustom;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Test
    void findCertificationsByRaceId() {
        final Race race = raceRepository.save(RaceFixture.createWithoutId());
        missionRepository.save(MissionFixture.createWithoutId());
        createCertifications(3);

        final Page<Certification> certifications = queryRepositoryCustom.findCertificationsByRaceId(race.getId(),
            PageRequest.of(0, 1, Sort.Direction.DESC, "id"));

        assertAll(
            () -> assertThat(certifications.getTotalPages()).isEqualTo(3),
            () -> assertThat(certifications.getContent().size()).isEqualTo(1),
            () -> assertThat(certifications.getContent().get(0).getId()).isEqualTo(1L)
        );
    }

    private void createCertifications(final int amount) {
        for (int i = 0; i < amount; i++) {
            certificationRepository.save(CertificationFixture.createCertificationWithoutId());
        }
    }
}