import { atom } from "recoil";

export const ridersInfoState = atom({
  key: "ridersInfoState",
  default: [],
});

export const riderInfoState = atom({
  key: "riderInfoState",
  default: null,
});
