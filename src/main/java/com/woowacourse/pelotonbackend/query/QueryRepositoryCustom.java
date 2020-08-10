package com.woowacourse.pelotonbackend.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowacourse.pelotonbackend.certification.domain.Certification;

public interface QueryRepositoryCustom {

    Page<Certification> findCertificationsByRaceId(Long raceId, Pageable pageable);
}
