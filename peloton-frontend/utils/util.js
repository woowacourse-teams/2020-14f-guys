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
