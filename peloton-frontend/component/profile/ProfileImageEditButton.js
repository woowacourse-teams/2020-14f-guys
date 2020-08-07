import React from "react";
import { Alert, Linking, TouchableOpacity } from "react-native";
import { useRecoilState, useRecoilValue } from "recoil";
import { userInfoState, userTokenState } from "../atoms";
import Axios from "axios";
import { SERVER_BASE_URL } from "../../utils/constants";
import * as ImagePicker from "expo-image-picker";

const ProfileImageEditButton = ({ children }) => {
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const token = useRecoilValue(userTokenState);

  const requestChangeImage = (selectedImage) => {
    const formData = new FormData();
    formData.append("profile_image", {
      uri: selectedImage,
      type: "image/jpeg",
      name: selectedImage.substring(9),
    });
    Axios.post(`${SERVER_BASE_URL}/api/members/profile`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${token}`,
      },
    }).catch((err) => alert(err));
  };

  const pickAndChangeProfileImage = async () => {
    const permissionResult = await ImagePicker.requestCameraRollPermissionsAsync();
    if (permissionResult.granted === false) {
      Alert.alert(
        "카메라 롤 권한이 없습니다.",
        "권한 설정 페이지로 이동하시겠습니까?",
        [
          {
            text: "Cancel",
            style: "cancel",
          },
          { text: "OK", onPress: async () => await Linking.openSettings() },
        ],
        { cancelable: false },
      );
    } else {
      const pickerResult = await ImagePicker.launchImageLibraryAsync({
        allowsEditing: true,
        aspect: [4, 3],
        quality: 0.1,
        base64: true,
      });
      if (pickerResult.cancelled === true) {
        console.log("cameraroll picker cancelled");
        return;
      }
      const selectedImage = pickerResult.uri;
      setUserInfo({
        ...userInfo,
        profile: {
          baseImageUrl: selectedImage,
        },
      });
      requestChangeImage(selectedImage);
    }
  };

  return (
    <TouchableOpacity onPress={pickAndChangeProfileImage}>
      {children}
    </TouchableOpacity>
  );
};

export default ProfileImageEditButton;
