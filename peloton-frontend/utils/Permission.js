import {
  requestCameraRollPermissionsAsync,
  requestCameraPermissionsAsync,
} from "expo-image-picker";
import { Alert, Linking } from "react-native";

export const getCameraRollPermission = async () => {
  const permissionResult = await requestCameraRollPermissionsAsync();
  if (permissionResult.granted === false) {
    Alert.alert(
      "사진 접근 권한이 없습니다.",
      "권한 설정 페이지로 이동하시겠습니까?",
      [
        {
          text: "Cancel",
          style: "cancel",
        },
        { text: "OK", onPress: async () => await Linking.openSettings() },
      ],
      { cancelable: false }
    );
  }
  return permissionResult.granted;
};

export const getCameraPermission = async () => {
  const permissionResult = await requestCameraPermissionsAsync();
  if (permissionResult.granted === false) {
    Alert.alert(
      "카메라 접근 권한이 없습니다.",
      "권한 설정 페이지로 이동하시겠습니까?",
      [
        {
          text: "Cancel",
          style: "cancel",
        },
        { text: "OK", onPress: async () => await Linking.openSettings() },
      ],
      { cancelable: false }
    );
  }
  return permissionResult.granted;
};
