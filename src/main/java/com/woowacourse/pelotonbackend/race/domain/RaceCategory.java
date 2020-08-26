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
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_1.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_2.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_3.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_4.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_5.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_6.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_7.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/TIME/RACE_TIME_8.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_TIME_CERTIFICATION_IMAGE")),
        Arrays.asList(
            new MissionInstruction("한 사람 당 손가락 하트 하나씩\n모아서 찍어주세요!😆"),
            new MissionInstruction("한 사람한테 외모를 몰아주세요!😁"),
            new MissionInstruction("각자의 핸드폰에 시간이 나오도록 해서\n사진을 찍어주세요!📸"),
            new MissionInstruction("모두의 발을 한 곳에 모아서\n사진을 찍어주세요!👠"),
            new MissionInstruction("모두의 팔꿈치를 한 곳에 붙여서\n사진을 찍어주세요!🤳")
        )
    ),

    STUDY(Arrays.asList(
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_1.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_2.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_3.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_4.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_5.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_6.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_7.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/STUDY/RACE_STDUY_8.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(
            new MissionInstruction("공부하는 책이 나오도록\n사진을 찍어주세요!📚"),
            new MissionInstruction("자신이 가장 아끼는 필기도구를 모아서\n사진을 찍어주세요!✏️"),
            new MissionInstruction("모두의 발을 한 곳에 모아서\n사진을 찍어주세요!👠"),
            new MissionInstruction("모두의 손을 한 곳에 붙여서\n사진을 찍어주세요!🖖"),
            new MissionInstruction("본인이 공부했음을 자유롭게 증명해보아요!🏅")
        )
    ),

    PLAY(Arrays.asList(
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_1.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_2.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_3.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_4.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_5.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(
            new MissionInstruction("어떤 여가활동을 즐기고 있는지 보여주세요!🤾‍"),
            new MissionInstruction("함께하는 사람과 사진을 찍어주세요!👫"),
            new MissionInstruction("모두의 발을 한 곳에 모아서\n사진을 찍어주세요!👠"),
            new MissionInstruction("모두의 손을 한 곳에 붙여서\n사진을 찍어주세요!🖖")
        )
    ),
    EXERCISE(Arrays.asList(
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_1.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_2.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_3.jpg"),
        new ImageUrl(
            "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_4.jpg"),
        new ImageUrl("https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/PLAY/race_play_5.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(
            new MissionInstruction("운동 중인 모습을 보여주세요!🏃‍"),
            new MissionInstruction("운동 중인 장소가 나오게 한 컷!🏋️‍"),
            new MissionInstruction("오늘 강화할 부위를 찍어주세요!🦵💪"),
            new MissionInstruction("오늘의 운동 복장은? 자랑해주세요!🩳")
        )
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
