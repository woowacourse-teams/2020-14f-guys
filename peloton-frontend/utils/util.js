import { CommonActions, TabActions } from "@react-navigation/native";
import { Alert } from "react-native";

export const navigateWithHistory = (navigation, routes) => {
  navigation.dispatch({
    ...CommonActions.reset({
      index: routes.length - 1,
      routes: routes,
    }),
  });
};

export const navigateWithoutHistory = (navigation, name) => {
  navigateWithHistory(navigation, [{ name }]);
};

export const navigateTabScreen = (navigation, name) => {
  navigation.dispatch(TabActions.jumpTo(name));
};

export const navigateToRaceDetail = (navigation, raceId) => {
  navigateWithHistory(navigation, [
    {
      name: "Home",
    },
    {
      name: "RaceDetail",
      params: {
        id: raceId,
      },
    },
  ]);
};

export const alertNotEnoughCash = ({ onOk }) => {
  Alert.alert(
    "잔액이 부족합니다.",
    "캐시 충전 페이지로 이동하시겠습니까?",
    [
      {
        text: "Cancel",
        style: "cancel",
      },
      {
        text: "OK",
        onPress: onOk,
      },
    ],
    { cancelable: false },
  );
};

export const customDateTimeParser = (input) => {
  const date = new Date(input);

  return `${date.getFullYear()}년 ${date.getMonth()}월 ${date.getDay()}일 ${customHourParser(
    date.getHours(),
  )}시 ${customMinuteParser(date.getMinutes())}분`;
};

export const customTimeParser = (input) => {
  const date = new Date(input);

  return `${customHourParser(date.getHours())}:${customMinuteParser(
    date.getMinutes(),
  )}`;
};

export const customHourParser = (hour) => {
  return hour > 12
    ? hour - 12 > 10
      ? `오후 ${hour - 12}`
      : `오후 0${hour - 12}`
    : hour > 10
    ? `오후 ${hour}`
    : `오후 0${hour}`;
};

export const customMinuteParser = (minute) => {
  return minute < 10 ? `0${minute}` : `${minute}`;
};
