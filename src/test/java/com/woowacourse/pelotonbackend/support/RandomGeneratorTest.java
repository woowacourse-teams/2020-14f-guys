package com.woowacourse.pelotonbackend.support;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomGeneratorTest {

    private RandomGenerator randomGenerator;

    @BeforeEach
    void setUp() {
        randomGenerator = new RandomGeneratorImpl();
    }

    @Test
    void randomStringTest() {
        final String randomString = randomGenerator.getRandomString();
        assertThat(randomString.length()).isEqualTo(8);
        assertThat(randomString).isNotEqualTo(randomGenerator.getRandomString());
    }

}
