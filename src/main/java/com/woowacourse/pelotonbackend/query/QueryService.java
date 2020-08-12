package com.woowacourse.pelotonbackend.query;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QueryService {
    private final MissionRepository missionRepository;
    private final CertificationRepository certificationRepository;

    public RaceCertificationsResponse findCertificationsByRaceId(final Long raceId, final Pageable pageable) {
        final List<Long> missionIds = missionRepository.findByRaceId(raceId).stream()
            .map(Mission::getId)
            .collect(Collectors.toList());
        Page<Certification> certifications = certificationRepository.findByMissionIds(missionIds, pageable);

        return RaceCertificationsResponse.of(certifications);
    }
}
