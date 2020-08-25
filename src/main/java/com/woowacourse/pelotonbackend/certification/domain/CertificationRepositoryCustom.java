package com.woowacourse.pelotonbackend.certification.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CertificationRepositoryCustom {

    Page<Certification> findByRiderId(long id, Pageable pageable);

    Page<Certification> findByMissionIdsAndStatus(List<Long> missionIds, CertificationStatus status,
        Pageable pageable);

    Page<Certification> findByMissionIdsAndRiderIds(List<Long> missionIds, List<Long> riderIds, Pageable pageable);

    boolean existsByRiderIdAndMissionId(long riderId, long missionId);
}
