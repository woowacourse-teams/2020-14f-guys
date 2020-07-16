package com.woowacourse.pelotonbackend.vo;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.validation.constraints.NotBlank;

import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import lombok.Value;

@Value
public class ImageUrl {
    @NotBlank
    private final String baseImageUrl;

    public static ImageUrl getRandomThumbnail(RaceCategory category) {
        return SampleUrlCash.getRandomImage(category, SampleUrlCash.ImageType.THUMBNAIL);
    }

    public static ImageUrl getRandomCertificationImage(RaceCategory category) {
        return SampleUrlCash.getRandomImage(category, SampleUrlCash.ImageType.CERTIFICATION);
    }

    private static class SampleUrlCash {
        private static final Map<RaceCategory, List<ImageUrl>> thumbnails = new HashMap<>();
        private static final Map<RaceCategory, List<ImageUrl>> certifications = new HashMap<>();

        private enum ImageType {
            THUMBNAIL(thumbnails),
            CERTIFICATION(certifications);

            private final Map<RaceCategory, List<ImageUrl>> map;

            ImageType(final Map<RaceCategory, List<ImageUrl>> map) {
                this.map = map;
            }

            public Map<RaceCategory, List<ImageUrl>> getMap() {
                return map;
            }
        }

        static {
            thumbnails.put(RaceCategory.TIME, Collections.singletonList(
                new ImageUrl("TEST_URL")
            ));
            certifications.put(RaceCategory.TIME, Collections.singletonList(
                new ImageUrl("TEST_CERTIFICATION_IMAGE")
            ));
        }

        static ImageUrl getRandomImage(RaceCategory category, ImageType type) {
            final List<ImageUrl> images = type.getMap().get(category);

            return images.get(generateRandom(images));
        }

        private static int generateRandom(final List<ImageUrl> images) {
            return new Random().nextInt(images.size());
        }
    }
}
