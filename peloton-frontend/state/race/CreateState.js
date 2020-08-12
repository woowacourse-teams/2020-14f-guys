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
  },
});
