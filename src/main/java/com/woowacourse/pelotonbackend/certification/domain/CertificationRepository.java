package com.woowacourse.pelotonbackend.certification.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CertificationRepository
    extends PagingAndSortingRepository<Certification, Long>, CertificationRepositoryCustom {
}
