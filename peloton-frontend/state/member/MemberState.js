import { atom } from "recoil";

export const memberTokenState = atom({
  key: "memberTokenState",
  default: "",
});

export const memberInfoState = atom({
  key: "memberInfoState",
  default: {
    id: "",
    kakao_id: "",
    profile:
      "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/basic.profile.image.png",
    name: "",
    email: "",
    cash: "",
    role: "",
  },
});

export const certificationMemberState = atom({
  key: "certificationMemberState",
  default: {
    id: "",
    kakao_id: "",
    profile:
      "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/basic.profile.image.png",
    name: "",
    email: "",
    cash: "",
    role: "",
  },
});
