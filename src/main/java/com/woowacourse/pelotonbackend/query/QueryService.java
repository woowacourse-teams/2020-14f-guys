package com.woowacourse.pelotonbackend.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QueryService {
    private final QueryRepositoryCustom queryRepository;

    public RaceCertificationsResponse findCertificationsByRaceId(final Long raceId, final Pageable pageable) {
        final Page<Certification> certifications = queryRepository.findCertificationsByRaceId(raceId, pageable);

        return RaceCertificationsResponse.of(certifications);
    }
}
