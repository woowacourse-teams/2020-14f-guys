import { atom } from "recoil";

export const userTokenState = atom({
  key: "userTokenState",
  default: null,
});

export const userInfoState = atom({
  key: "userInfoState",
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
