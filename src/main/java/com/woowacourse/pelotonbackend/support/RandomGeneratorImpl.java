package com.woowacourse.pelotonbackend.support;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomGeneratorImpl implements RandomGenerator {
    private final Random random;

    public RandomGeneratorImpl() {
        this.random = new Random();
    }

    public int getRandomIntLowerThan(final int bound) {
        return random.nextInt(bound);
    }
}
