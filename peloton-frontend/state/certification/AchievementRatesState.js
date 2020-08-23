import { atom } from "recoil";

export const achievementRatesState = atom({
  key: "achievementRatesState",
  default: [
    {
      member_id: "",
      member_name: "",
      certification_count: "",
      achievement: "",
    },
  ],
});
