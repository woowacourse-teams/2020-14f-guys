package com.woowacourse.pelotonbackend.member.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.pelotonbackend.common.exception.MoneyInvalidException;
import com.woowacourse.pelotonbackend.vo.Cash;

class MemberTest {

    @DisplayName("회원의 금액을 충전한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,51000", "5000,55000"}, delimiter = ',')
    void plusCash(BigDecimal plusMoney, BigDecimal result) {
        final Member member = MemberFixture.createWithId(1L);
        final Member cashUpdatedMember = member.plusCash(new Cash(plusMoney));

        assertThat(cashUpdatedMember.getCash().getCash()).isEqualTo(result);
    }

    @DisplayName("회원의 금액을 차감한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,4000", "5000,0"}, delimiter = ',')
    void minusCash(BigDecimal minusMoney, BigDecimal result) {
        final Member member = MemberFixture.createWithId(1L)
            .toBuilder()
            .cash(new Cash(BigDecimal.valueOf(5000)))
            .build();
        final Member cashUpdatedMember = member.minusCash(new Cash(minusMoney));

        assertThat(cashUpdatedMember.getCash().getCash()).isEqualTo(result);
    }

    @DisplayName("현재 금액이 더 적은 경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(longs = {1000, 5000})
    void minusInvalidCash(long minusMoney) {
        final Member member = MemberFixture.createWithId(1L)
            .toBuilder()
            .cash(new Cash(BigDecimal.valueOf(999)))
            .build();
        assertThatThrownBy(() -> member.minusCash(new Cash(BigDecimal.valueOf(minusMoney))))
            .isInstanceOf(MoneyInvalidException.class)
            .hasMessage("잔액이 부족합니다. 충전 후 이용해주세요.");
    }

    @DisplayName("이름을 정상적으로 변경한다.")
    @ParameterizedTest
    @CsvSource(value = {"시영,시영","시카,시카", " , "})
    void changeName(String value, String expected) {
        final Member member = MemberFixture.createWithId(1L);
        final Member nameUpdatedMember = member.changeName(value);

        assertThat(nameUpdatedMember.getName()).isEqualTo(expected);
    }
}