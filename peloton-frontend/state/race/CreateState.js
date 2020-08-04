import { atom } from "recoil";

export const raceCreateInfoState = atom({
  key: "raceCreateInfoState",
  default: {
    title: "",
    description: "",
    startDate: "",
    endDate: "",
    category: "",
    entranceFee: "",
  },
});
