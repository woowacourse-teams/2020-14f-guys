package com.woowacourse.pelotonbackend.member.application;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.TestExecutionListeners;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class MemberServiceCacheTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("member").clear();
        memberService.createMember(MemberFixture.createRequest(1000L, "email@email.com", "name"));
    }

    @DisplayName("처음 멤버를 조회한 이후에, 캐싱되는 것을 확인한다")
    @Test
    void cacheFind() {
        assertThat(getCachedMember(1L)).isNull();
        final MemberResponse member = memberService.findMember(1L);

        assertThat(member).isEqualToComparingFieldByField(getCachedMember(1L));
    }

    @DisplayName("멤버 업데이트 후, 캐싱된 데이터가 사라진다.")
    @Test
    void cacheEvictAfterUpdate() {
        memberService.findMember(1L);
        memberService.updateName(1L, MemberFixture.createNameUpdateRequest());

        assertThat(getCachedMember(1L)).isNull();
    }

    @DisplayName("멤버 삭제 후, 캐싱된 데이터가 사라진다.")
    @Test
    void cacheEvictAfterDelete() {
        memberService.findMember(1L);
        memberService.deleteById(1L);

        assertThat(getCachedMember(1L)).isNull();
    }

    private MemberResponse getCachedMember(final Long id) {
        return cacheManager.getCache("member").get(id, MemberResponse.class);
    }
}