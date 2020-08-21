import { atom } from "recoil";

export const certificationsState = atom({
  key: "certificationsState",
  default: [],
});

export const certificationDetailState = atom({
  key: "certificationDetailState",
  default: {
    certification: null,
    mission: null,
    member: null,
  },
});
