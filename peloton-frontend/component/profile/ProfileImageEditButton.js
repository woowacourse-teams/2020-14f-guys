import React from "react";
import { Alert, Linking, TouchableOpacity } from "react-native";
import { useRecoilState, useRecoilValue } from "recoil";
import * as ImagePicker from "expo-image-picker";
import { MemberApi } from "../../utils/api/MemberApi";
import { memberInfoState, memberTokenState } from "../../state/member/MemberState";

const ProfileImageEditButton = ({ children }) => {
  const [userInfo, setUserInfo] = useRecoilState(memberInfoState);
  const token = useRecoilValue(memberTokenState);

  const requestChangeImage = async (selectedImage) => {
    const formData = new FormData();
    formData.append("profile_image", {
      uri: selectedImage,
      type: "image/jpeg",
      name: selectedImage.substring(9),
    });
    try {
      const profile = await MemberApi.postProfile(token, formData);
      setUserInfo({
        ...userInfo,
        profile,
      });
    } catch (error) {
      alert("에러가 발생했습니다.");
    }
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
        quality: 0.01,
        base64: true,
      });
      if (pickerResult.cancelled === true) {
        console.log("cameraroll picker cancelled");
        return;
      }
      const selectedImage = pickerResult.uri;
      setUserInfo({
        ...userInfo,
        profile: selectedImage,
      });
    }
  };

  return (
    <TouchableOpacity onPress={pickAndChangeProfileImage}>
      {children}
    </TouchableOpacity>
  );
};

export default ProfileImageEditButton;
