package com.woowacourse.pelotonbackend.support;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomGeneratorImpl implements RandomGenerator {
    private static final int STRING_LENGTH = 8;
    private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SHA_256 = "SHA-256";

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

    @Override
    public String getRandomSHA256(final String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            byte[] encodedhash = digest.digest(
                message.getBytes(StandardCharsets.UTF_8));

            return byteArrayToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("SHA-256 algorithm should exists in MessageDigest");
        }
    }

    private String byteArrayToString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }

        return stringBuilder.toString();
    }
}
