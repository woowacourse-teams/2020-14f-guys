package com.woowacourse.pelotonbackend.pendingcash;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PendingCashService {
    private final PendingCashRepository pendingCashRepository;

    public void create(final Long memberId, final Cash cash) {
        final PendingCash pendingCash = PendingCash.builder()
            .memberId(AggregateReference.to(memberId))
            .cash(cash)
            .cashStatus(CashStatus.PENDING)
            .build();
        pendingCashRepository.save(pendingCash);
    }
}
