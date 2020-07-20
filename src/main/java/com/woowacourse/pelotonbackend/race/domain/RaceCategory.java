package com.woowacourse.pelotonbackend.race.domain;

import java.util.Arrays;
import java.util.List;

import com.woowacourse.pelotonbackend.support.RandomGenerator;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RaceCategory {
    TIME(Arrays.asList(new ImageUrl("TEST_URL")),
        Arrays.asList(new ImageUrl("TEST_CERTIFICATION_IMAGE")));

    private final List<ImageUrl> thumbnails;
    private final List<ImageUrl> certifications;

    public ImageUrl getRandomThumbnail(RandomGenerator randomGenerator) {
        return thumbnails.get(randomGenerator.getRandomIntLowerThan(thumbnails.size()));
    }

    public ImageUrl getRandomCertification(RandomGenerator randomGenerator) {
        return certifications.get(randomGenerator.getRandomIntLowerThan(certifications.size()));
    }
}
