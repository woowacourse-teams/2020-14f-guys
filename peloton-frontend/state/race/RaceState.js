import { atom } from "recoil";

export const raceCreateInfoState = atom({
  key: "raceCreateInfoState",
  default: {
    title: "",
    description: "",
    start_date: "",
    end_date: "",
    category: "",
    entrance_fee: "",
    days: [],
    mission_start_time: "",
    mission_end_time: "",
  },
});

export const raceInfoState = atom({
  key: "raceInfoState",
  default: {
    id: null,
    category: null,
    certification_example: null,
    description: null,
    entrance_fee: null,
    race_duration: {
      start_date: null,
      end_date: null,
    },
    thumbnail: null,
    title: null,
  },
});
