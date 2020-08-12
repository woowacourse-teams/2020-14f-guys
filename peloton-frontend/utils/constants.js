import { animated } from "react-spring";
import { Image, Text, View } from "react-native";

export const COLOR = {
  RED: "#ce1313",
  GREEN1: "#B0D4D9",
  GREEN2: "#6e8ca0",
  GREEN3: "#334856",
  BLUE1: "#B0C0D9",
  BLUE2: "#0b53e8",
  BLUE3: "#4f83dd",
  BLUE4: "#284170",
  BLUE5: "#21365d",
  ARROW_BLUE: "#61779f",
  PURPLE: "#B5B0D9",
  LAVENDER: "#CAB0D9",
  PINK: "#D0B0D4",
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
    title: "레이스1",
    src:
      "https://i.pinimg.com/736x/fd/e7/6d/fde76d74009056da95e35abec597f22e.jpg",
    text:
      "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요",
  },
  {
    title: "레이스2",
    src:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQqH2LDKlEd-C6ahSmsVLD0LLAnTc4oy7NAtw&usqp=CAU",
    text: "레이스2",
  },
  {
    title: "레이스3",
    src: "https://pbs.twimg.com/media/EADIWaaU4AI5lK_.jpg:small",
    text: "레이스3",
  },
  {
    title: "레이스4",
    src: "https://t1.daumcdn.net/cfile/tistory/2563273358A2A65114",
    text: "레이스4",
  },
];

export const IMAGE_URL =
  "https://t1.daumcdn.net/cfile/tistory/2657B9505809B4B634";

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
];

export const CATEGORY = [
  {
    title: "모임",
    subtitle: "Ice Breaking",
    category: "TIME",
    src: "https://img.sbs.co.kr/newimg/news/20181019/201240131_1280.jpg",
  },
  {
    title: "학습",
    subtitle: "Learning",
    category: "STUDY",
    src: "https://vitahani.com/assets/20190625085334.jpg",
  },
  {
    title: "제목",
    subtitle: "subtitle",
    category: "RACE_CATEGORY_VALUE",
    src:
      "https://x86.co.kr/files/attach/images/1951610/342/576/002/0555addbb9bfd672918a2539668f0dac.jpg",
  },
];
export const SERVER_BASE_URL = "https://peloton.ga";
export const TOKEN_STORAGE = "@token_storage";

export const AnimatedImage = animated(Image);
export const AnimatedView = animated(View);
export const AnimatedText = animated(Text);
