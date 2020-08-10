package com.woowacourse.pelotonbackend.race.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.woowacourse.pelotonbackend.mission.domain.MissionInstruction;
import com.woowacourse.pelotonbackend.support.RandomGenerator;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RaceCategory {
    TIME(Collections.singletonList(new ImageUrl("TEST_TIME_THUMBNAIL_URL")),
        Collections.singletonList(new ImageUrl("TEST_TIME_CERTIFICATION_IMAGE")),
        Arrays.asList(
            new MissionInstruction("시계를 다리 사이에 두고 물구나무서서 찍기"),
            new MissionInstruction("약속한 사람끼리 모여서 손으로 별 그리고 사진 찍기"))),
    STUDY(Collections.singletonList(new ImageUrl("TEST_STUDY_THUMBNAIL_URL")),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(
            new MissionInstruction("스터디원끼리 모여서 점프샷 찍기"),
            new MissionInstruction("스터디 이름이 쓰여진 포스트잇 하나씩 들고 찍기")
        ));

    private final List<ImageUrl> thumbnails;
    private final List<ImageUrl> certifications;
    private final List<MissionInstruction> missionInstructions;

    public ImageUrl getRandomThumbnail(RandomGenerator randomGenerator) {
        return thumbnails.get(randomGenerator.getRandomIntLowerThan(thumbnails.size()));
    }

    public ImageUrl getRandomCertification(RandomGenerator randomGenerator) {
        return certifications.get(randomGenerator.getRandomIntLowerThan(certifications.size()));
    }

    public MissionInstruction getRandomMissionInstruction(RandomGenerator randomGenerator) {
        return missionInstructions.get(randomGenerator.getRandomIntLowerThan(missionInstructions.size()));
    }
}
