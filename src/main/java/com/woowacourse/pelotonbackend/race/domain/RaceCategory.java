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
            "https://www.urbanbrush.net/web/wp-content/uploads/edd/2019/08/urbanbrush-20190813123337707709.png"),
        new ImageUrl("https://cdn.pixabay.com/photo/2019/08/13/17/18/fantasy-4403840_960_720.jpg"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTf5yLpCcuRE0Rz8lk3APwCM5jQ2zfKysoVwA&usqp=CAU"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSJe91_ulqd9ZvkjTN5DtCO192UKFYyqmP4Fw&usqp=CAU"),
        new ImageUrl("https://cdn.pixabay.com/photo/2018/12/25/21/39/nature-3894863_960_720.jpg"),
        new ImageUrl("https://wallpapercave.com/wp/wp4443721.jpg"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ-GstBJeL-1oN8WJldyoloGjR0FYYXXGM4Tg&usqp=CAU"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRcpUWervDa-bzCgSwhadgnHcGYx8IldJZclA&usqp=CAU"),
        new ImageUrl("https://i.pinimg.com/originals/11/00/a5/1100a50f3ae0630470cbbcf6a537dce6.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_TIME_CERTIFICATION_IMAGE")),
        Arrays.asList(new MissionInstruction("시계를 다리 사이에 두고 물구나무서서 찍기"),
            new MissionInstruction("약속한 사람끼리 모여서 손으로 별 그리고 사진 찍기"))
    ),

    STUDY(Arrays.asList(
        new ImageUrl(
            "https://www.urbanbrush.net/web/wp-content/uploads/edd/2019/08/urbanbrush-20190813123337707709.png"),
        new ImageUrl("https://cdn.pixabay.com/photo/2019/08/13/17/18/fantasy-4403840_960_720.jpg"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTf5yLpCcuRE0Rz8lk3APwCM5jQ2zfKysoVwA&usqp=CAU"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSJe91_ulqd9ZvkjTN5DtCO192UKFYyqmP4Fw&usqp=CAU"),
        new ImageUrl("https://cdn.pixabay.com/photo/2018/12/25/21/39/nature-3894863_960_720.jpg"),
        new ImageUrl("https://wallpapercave.com/wp/wp4443721.jpg"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ-GstBJeL-1oN8WJldyoloGjR0FYYXXGM4Tg&usqp=CAU"),
        new ImageUrl(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRcpUWervDa-bzCgSwhadgnHcGYx8IldJZclA&usqp=CAU"),
        new ImageUrl("https://i.pinimg.com/originals/11/00/a5/1100a50f3ae0630470cbbcf6a537dce6.jpg")
    ),
        Collections.singletonList(new ImageUrl("TEST_STUDY_CERTIFICATION_IMAGE")),
        Arrays.asList(new MissionInstruction("스터디원끼리 모여서 점프샷 찍기"),
            new MissionInstruction("스터디 이름이 쓰여진 포스트잇 하나씩 들고 찍기"))
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
