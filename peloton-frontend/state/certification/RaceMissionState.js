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
  date.setSeconds(today.getSeconds() + 600);
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
        start_time: todayOneHourBefore,
        end_time: todayOneHourAfter,
      },
      mission_instruction: "한명에게 별을 몰아주고 사진을 찍어요!",
      race_id: 1,
    },
  },
  {
    race: {
      id: 2,
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
        start_time: todayOneHourAfter,
        end_time: todayTwoHourAfter,
      },
      mission_instruction: "지건을 찌르세요",
      race_id: 2,
    },
  },
];
