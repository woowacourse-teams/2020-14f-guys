import { animated } from "react-spring";
import { Dimensions, Image, Text, View } from "react-native";
import * as Linking from "expo-linking";

export const COLOR = {
  RED: "#ce1313",
  GREEN1: "#B0D4D9",
  GREEN2: "#6e8ca0",
  GREEN3: "#334856",
  GREEN4: "rgb(52,199,89)",
  BLUE1: "#B0C0D9",
  BLUE2: "#0b53e8",
  BLUE3: "#4f83dd",
  BLUE4: "#284170",
  BLUE5: "#21365d",
  BLUE6: "#4261FD",
  ARROW_BLUE: "#61779f",
  PURPLE: "#B5B0D9",
  LAVENDER: "#CAB0D9",
  PINK: "rgb(255,45,85)",
  INDIAN_PINK: "#D9B0C0",
  WHITE: "#FFFFFF",
  WHITE2: "#F2F2F2",
  WHITE3: "#F0F3f4",
  WHITE4: "#eceff0",
  GRAY1: "rgb(142,142,147)",
  GRAY2: "rgb(174,174,178)",
  GRAY3: "rgb(199,199,204)",
  GRAY4: "rgb(209,209,214)",
  GRAY5: "rgb(229,229,234)",
  GRAY6: "rgb(242,242,247)",
  DARK_GRAY6: "rgb(28,28,30)",
  BLACK: "#000",
  BLACK2: "#1b1c20",
};
export const SAMPLE_IMAGES = [
  {
    id: 1,
    title: "레이스1",
    thumbnail:
      "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail1.png",
    description:
      "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요",
  },
  {
    id: 2,
    title: "레이스2",
    thumbnail:
      "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail2.jpg",
    description: "레이스2",
  },
  {
    id: 3,
    title: "레이스3",
    thumbnail:
      "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail3.jpeg",
    description: "레이스3",
  },
  {
    id: 4,
    title: "레이스4",
    thumbnail:
      "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/race-thumbnail-image/thumbnail4.jpeg",
    description: "레이스4",
  },
];

export const MOCK_DATA = [
  {
    url:
      "https://i.pinimg.com/736x/5f/f3/d7/5ff3d71b5834971c30af475c99f67c02.jpg",
  },
  {
    url:
      "https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F994A57495BB704E012",
  },
  {
    url:
      "https://cdn.pixabay.com/photo/2019/08/01/12/36/illustration-4377408_960_720.png",
  },
  {
    url: "https://www.queen.co.kr/news/photo/201910/320536_59033_3350.jpg",
  },
  {
    url:
      "https://i.pinimg.com/736x/52/b8/fb/52b8fb8d3607c58679393421478cd3ef.jpg",
  },
  {
    url:
      "https://www.10wallpaper.com/wallpaper/medium/1508/sky_lonely_tree_summer-Scenery_HD_Wallpaper_medium.jpg",
  },
];

export const SAMPLE_SUBTITLE = [
  "당신만의 레이스를 \n달려보세요! 😆",
  "오늘도 힘차게 \n도전하는 당신! 😁",
  "오늘 날씨가 \n참 좋네요! 😝",
  "친구들과 함께 해보아요! 😎",
];

export const RACE_CATEGORY_TIME = "TIME";
export const RACE_CATEGORY_STUDY = "STUDY";
export const RACE_CATEGORY_PLAY = "PLAY";
export const RACE_CATEGORY_EXERCISE = "EXERCISE";

export const SERVER_BASE_URL = "https://peloton.ga";
export const TOKEN_STORAGE = "@token_storage";
export const DEEP_LINK_BASE_URL = Linking.makeUrl("/");

export const AnimatedImage = animated(Image);
export const AnimatedView = animated(View);
export const AnimatedText = animated(Text);

export const RaceCreateUnitType = {
  TEXT: "text",
  DATE: "date",
  TIME: "time",
  DAYS: "days",
};

export const DAYS = [
  "SUNDAY",
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
];

export const CERTIFICATION_TYPE = {
  AVAILABLE: {
    activeOpacity: 0.7,
    blurRadius: 0,
    color: "rgba(52,199,89,0.3)",
    typeColor: "rgba(52,199,89,0.7)",
    backgroundColor: "#111",
    message: "인증 가능",
  },
  DONE: {
    activeOpacity: 0.7,
    blurRadius: 0,
    color: "rgba(52,199,89,0.3)",
    typeColor: "rgba(52,199,89,0.7)",
    backgroundColor: "#111",
    message: "인증 완료",
  },
  WAIT: {
    activeOpacity: 1,
    blurRadius: 3,
    color: "#444",
    typeColor: "#444",
    backgroundColor: "#666",
    message: "인증 대기",
  },
};

export const deviceWidth = Dimensions.get("window").width;
export const deviceHeight = Dimensions.get("window").height;
