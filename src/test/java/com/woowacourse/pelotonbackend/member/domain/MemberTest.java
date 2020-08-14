package com.woowacourse.pelotonbackend.member.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.woowacourse.pelotonbackend.vo.Cash;

class MemberTest {

    @ParameterizedTest
    @CsvSource(value = {"1000,1001", "5000,5001"}, delimiter = ',')
    void plusCash(BigDecimal plusMoney, BigDecimal result) {
        final Member member = MemberFixture.createWithId(1L);
        final Member cashUpdatedMember = member.plusCash(new Cash(plusMoney));

        assertThat(cashUpdatedMember.getCash().getCash()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"1000,-999", "5000,-4999"}, delimiter = ',')
    void minusCash(BigDecimal minusMoney, BigDecimal result) {
        final Member member = MemberFixture.createWithId(1L);
        final Member cashUpdatedMember = member.minusCash(new Cash(minusMoney));

        assertThat(cashUpdatedMember.getCash().getCash()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"시영,시영","시카,시카", " , "})
    void changeName(String value, String expected) {
        final Member member = MemberFixture.createWithId(1L);
        final Member nameUpdatedMember = member.changeName(value);

        assertThat(nameUpdatedMember.getName()).isEqualTo(expected);
    }
}