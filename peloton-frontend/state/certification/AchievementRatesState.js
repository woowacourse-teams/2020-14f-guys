import { atom } from "recoil";

export const achievementRatesState = atom({
  key: "achievementRatesState",
  default: [
    {
      race_id: "",
      race_title: "",
      certification_count: "",
      achievement: "",
    },
  ],
});
