import { atom } from "recoil";

export const raceCertificationState = atom({
  key: "RaceCertificationState",
  default: [
    {
      race: {
        id: "",
        title: "",
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
  date.setHours(today.getHours() + 1);
  return date;
})();
const todayTwoHourAfter = (() => {
  const date = new Date();
  date.setHours(today.getHours() + 2);
  return date;
})();

export const raceCertificationFixture = [
  {
    race: {
      id: 1,
      title: "레이스1",
      certification_example:
        "https://lh3.googleusercontent.com/W-z2puouUJ_ZDRiZxZ4NJl1Mi-TDJ9NVTz6XBbtO17po8Ave86MTPin_9NVm6_QF-V8",
      thumbnail:
        "https://opgg-com-image.akamaized.net/attach/images/20191215131450.859400.jpg",
    },
    mission: {
      id: 1,
      mission_duration: {
        start_time: todayOneHourBefore,
        end_time: todayOneHourAfter,
      },
      mission_instruction: "점프를 뛰세요",
      race_id: 1,
    },
  },
  {
    race: {
      id: 2,
      title: "레이스2",
      certification_example:
        "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng",
      thumbnail:
        "https://opgg-com-image.akamaized.net/attach/images/20191215131450.859400.jpg",
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
