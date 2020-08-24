package com.woowacourse.pelotonbackend.pendingcash;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;

@ExtendWith(MockitoExtension.class)
class PendingCashServiceTest {
    private PendingCashService pendingCashService;

    @Mock
    private PendingCashRepository pendingCashRepository;

    @BeforeEach
    void setUp() {
        pendingCashService = new PendingCashService(pendingCashRepository);
    }

    @DisplayName("Pending Cash를 정상적으로 저장한다.")
    @Test
    void create() {
        pendingCashService.create(MemberFixture.MEMBER_ID, MemberFixture.CASH);
        verify(pendingCashRepository).save(any(PendingCash.class));
    }
}