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
    days: ["MONDAY", "TUESDAY", "FRIDAY"],
    certification_available_duration: {
      start_time: "08:00:00",
      end_time: "10:00:00",
    },
  },
});

export const raceInfoState = atom({
  key: "raceInfoState",
  default: {
    id: null,
    category: null,
    certificationExample: {
      baseImageUrl: null,
    },
    description: null,
    entranceFee: {
      cash: null,
    },
    raceDuration: {
      startDate: null,
      endDate: null,
    },
    thumbnail: {
      baseImageUrl: null,
    },
    title: null,
    days: null,
    certification_available_duration: {
      start_time: null,
      end_time: null,
    },
  },
});
