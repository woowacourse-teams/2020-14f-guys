import { atom } from "recoil";

export const raceAchievementState = atom({
  key: "raceAchievementState",
  default: [
    {
      member_id: "",
      member_name: "",
      certification_count: "",
      achievement: "",
      prize: "",
    },
  ],
});
