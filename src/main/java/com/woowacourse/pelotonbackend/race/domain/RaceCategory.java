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
    TIME(Arrays.asList(
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail1.png"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail2.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail3.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail4.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail5.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail6.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail7.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail8.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail9.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_TIME_CERTIFICATION_IMAGE")),
        Arrays.asList(new MissionInstruction("시계를 다리 사이에 두고 물구나무서서 찍기"),
            new MissionInstruction("약속한 사람끼리 모여서 손으로 별 그리고 사진 찍기"))
    ),

    STUDY(Arrays.asList(
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail1.png"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail2.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail3.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail4.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail5.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail6.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail7.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail8.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail9.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(new MissionInstruction("스터디원끼리 모여서 점프샷 찍기"),
            new MissionInstruction("스터디 이름이 쓰여진 포스트잇 하나씩 들고 찍기"))
    ),

    PLAY(Arrays.asList(
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail1.png"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail2.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail3.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail4.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail5.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail6.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail7.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail8.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail9.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(new MissionInstruction("친구들과 함께 웃으면서 셀카 찍기!"),
            new MissionInstruction("맛있는 음식과 함께 브이 하기!"))
    ),
    EXERCISE(Arrays.asList(
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail1.png"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail2.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail3.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail4.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail5.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail6.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail7.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail8.jpeg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail9.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(new MissionInstruction("운동 시작시간과 끝낸 시간 인증하기!"),
            new MissionInstruction("자신이 가는 헬스장에서 날짜가 보이게 사진 찍기!"))
    );

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
