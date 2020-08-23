import { atom } from "recoil";

export const raceAchievementState = atom({
  key: "raceAchievementState",
  default: [
    {
      race_id: "",
      race_title: "",
      total_mission_count: "",
      achievement_rate: [
        {
          member_id: "",
          member_name: "",
          certification_count: "",
          achievement: "",
          prize: "",
        },
      ],
    },
  ],
});
