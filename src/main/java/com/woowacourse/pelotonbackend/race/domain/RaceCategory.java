package com.woowacourse.pelotonbackend.race.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RaceCategory {
    TIME(Arrays.asList(new ImageUrl("TEST_URL")),
        Arrays.asList(new ImageUrl("TEST_CERTIFICATION_IMAGE")));

    private final List<ImageUrl> thumbnails;
    private final List<ImageUrl> certifications;

    public ImageUrl getRandomThumbnail() {
        return thumbnails.get(generateRandom(thumbnails.size()));
    }

    public ImageUrl getRandomCertification() {
        return certifications.get(generateRandom(certifications.size()));
    }

    private int generateRandom(final int size) {
        return new Random().nextInt(size);
    }
}
