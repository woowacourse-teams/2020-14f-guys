import { atom } from "recoil";

export const raceMissionState = atom({
  key: "raceMissionState",
  default: [
    {
      race: {
        id: "",
        title: "",
        certification_example: "",
        thumbnail: "",
      },
      rider: {
        id: "",
      },
      mission: {
        id: "",
        mission_duration: {
          start_time: "",
          end_time: "",
        },
        mission_instruction: "",
        race_id: "",
      },
      certification: {
        id: "",
      },
    },
  ],
});

const today = new Date();
const todayOneHourBefore = (() => {
  const date = new Date();
  date.setHours(today.getHours() - 1);
  return date;
})();
const todayOneHourAfter = (() => {
  const date = new Date();
  // date.setHours(today.getHours() + 1);
  date.setSeconds(today.getSeconds() + 10);
  return date;
})();
const todayTwoHourAfter = (() => {
  const date = new Date();
  date.setHours(today.getHours() + 2);
  return date;
})();

export const raceMissionFixture = [
  {
    race: {
      id: 1,
      title: "운동",
      certification_example:
        "https://scontent-ssn1-1.xx.fbcdn.net/v/t1.0-9/52141277_2197572873680103_3494833992769732608_n.jpg?_nc_cat=110&_nc_sid=8024bb&_nc_ohc=7FhhUh4U9WEAX9PRgmy&_nc_ht=scontent-ssn1-1.xx&oh=ad66b941b3527a5d59d269d6afb3d87c&oe=5F5C4AAF",
      thumbnail:
        "https://post-phinf.pstatic.net/MjAxOTEyMTFfMjgy/MDAxNTc2MDUwODAxODQ3.qV7kcYtxZZr7ycnGUsmKyj2-eYBThcJbaAf7Xr4uLzAg.T0x40qYIWBL2pXFG51QhCt6N5xSFZVKOZaicP3XGx5Yg.JPEG/image_8240955871576050766062.jpg?type=w1200",
    },
    rider: {
      id: 1,
    },
    mission: {
      id: 1,
      mission_duration: {
        start_time: todayOneHourBefore.toISOString(),
        end_time: todayOneHourAfter.toISOString(),
      },
      mission_instruction: "한명에게 별을 몰아주고 사진을 찍어요!",
      race_id: 1,
    },
    certification: null,
  },
  {
    race: {
      id: 3,
      title: "모임",
      certification_example:
        "https://i.pinimg.com/originals/34/9c/25/349c257611365c8380df8c21015d0279.jpg",
      thumbnail: "https://m1.daumcdn.net/cfile176/image/99B382425D105DE608474D",
    },
    rider: {
      id: 3,
    },
    mission: {
      id: 3,
      mission_duration: {
        start_time: todayOneHourBefore.toISOString(),
        end_time: todayOneHourAfter.toISOString(),
      },
      mission_instruction: "쪼르륵 앉아서 한쪽으로 기울이고 사진을 찍어요!",
      race_id: 3,
    },
    certification: {
      id: 3,
      image:
        "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/certification.image/2482C991-AB0A-4B1B-9198-2E9E20D103A6_1_105_c.jpeg",
    },
  },
  {
    race: {
      id: 1,
      title: "기상",
      certification_example:
        "https://i.pinimg.com/474x/fd/8c/20/fd8c20bc1b23f05d33385e11741a6e0f.jpg",
      thumbnail:
        "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/Jp6/image/92CQBbgioOWYSe-SFIUkkjJEGpQ.jpg",
    },
    rider: {
      id: 1,
    },
    mission: {
      id: 2,
      mission_duration: {
        start_time: todayOneHourAfter.toISOString(),
        end_time: todayTwoHourAfter.toISOString(),
      },
      mission_instruction: "지건을 찌르세요",
      race_id: 1,
    },
  },
];
export const raceCertificationState = atom({
  key: "raceCertificationState",
  default: null,
});
