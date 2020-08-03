package com.woowacourse.pelotonbackend.race.domain;

import java.util.Collections;
import java.util.List;

import com.woowacourse.pelotonbackend.support.RandomGenerator;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RaceCategory {
    TIME(Collections.singletonList(new ImageUrl("TEST_TIME_THUMBNAIL_URL")),
        Collections.singletonList(new ImageUrl("TEST_TIME_CERTIFICATION_IMAGE"))),
    STUDY(Collections.singletonList(new ImageUrl("TEST_STUDY_THUMBNAIL_URL")),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")));

    private final List<ImageUrl> thumbnails;
    private final List<ImageUrl> certifications;

    public ImageUrl getRandomThumbnail(RandomGenerator randomGenerator) {
        return thumbnails.get(randomGenerator.getRandomIntLowerThan(thumbnails.size()));
    }

    public ImageUrl getRandomCertification(RandomGenerator randomGenerator) {
        return certifications.get(randomGenerator.getRandomIntLowerThan(certifications.size()));
    }
}
