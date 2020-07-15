package com.woowacourse.pelotonbackend.certification.service;

import org.springframework.stereotype.Service;

import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;

@Service
public class CertificationService {
    public Long create(final String baseUrl, final CertificationStatus status, final String description,
        final Long riderId, final Long missionId) {

        return 1L;
    }
}
