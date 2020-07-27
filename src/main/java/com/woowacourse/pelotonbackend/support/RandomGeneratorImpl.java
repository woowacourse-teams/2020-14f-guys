package com.woowacourse.pelotonbackend.support;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomGeneratorImpl implements RandomGenerator {
    private static final int STRING_LENGTH = 8;
    private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random;

    public RandomGeneratorImpl() {
        this.random = new Random();
    }

    public int getRandomIntLowerThan(final int bound) {
        return random.nextInt(bound);
    }

    public String getRandomString() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            sb.append(RANDOM_STRING.charAt(random.nextInt(RANDOM_STRING.length())));
        }
        return sb.toString();
    }
}
