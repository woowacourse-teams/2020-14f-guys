package com.woowacourse.pelotonbackend.rider.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class RiderService {
    private final RiderRepository riderRepository;

    public Long create(final RiderCreateRequest riderCreateRequest) {
        return riderRepository.save(riderCreateRequest.toRider()).getId();
    }
}
