package com.woowacourse.pelotonbackend.rider.domain;

import org.springframework.data.repository.CrudRepository;

public interface RiderRepository extends CrudRepository<Rider, Long>, RiderCustomRepository {
}
