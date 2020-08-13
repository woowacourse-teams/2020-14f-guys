import { atom } from "recoil";

export const memberTokenState = atom({
  key: "memberTokenState",
  default: null,
});

export const memberInfoState = atom({
  key: "memberInfoState",
  default: {
    id: null,
    kakao_id: null,
    profile: null,
    name: null,
    email: null,
    cash: null,
    role: null,
  },
});
