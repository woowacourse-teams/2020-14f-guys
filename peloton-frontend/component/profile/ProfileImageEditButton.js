import React from "react";
import { StyleSheet, Alert, Linking, TouchableOpacity } from "react-native";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import * as ImagePicker from "expo-image-picker";

import { MemberApi } from "../../utils/api/MemberApi";
import {
  memberInfoState,
  memberTokenState,
} from "../../state/member/MemberState";
import { loadingState } from "../../state/loading/LoadingState";
import { getCameraRollPermission } from "../../utils/Permission";

const ProfileImageEditButton = ({ children }) => {
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const setIsLoading = useSetRecoilState(loadingState);
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
      setMemberInfo({
        ...memberInfo,
        profile,
      });
    } catch (error) {
      alert("에러가 발생했습니다.");
    }
  };

  const pickAndChangeProfileImage = async () => {
    setIsLoading(true);
    const hasCameraRollPermission = await getCameraRollPermission();
    if (hasCameraRollPermission === false) {
      setIsLoading(false);
      return;
    }
    const pickerResult = await ImagePicker.launchImageLibraryAsync({
      allowsEditing: true,
      aspect: [1, 1],
      base64: true,
      quality: 0.1,
    });

    if (pickerResult.cancelled === true) {
      setIsLoading(false);
      return;
    }
    const selectedImage = pickerResult.uri;
    await requestChangeImage(selectedImage);
    setIsLoading(false);
  };

  return (
    <TouchableOpacity
      style={styles.profileImageEditButton}
      onPress={pickAndChangeProfileImage}
    >
      {children}
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  profileImageEditButton: {
    width: 125,
    height: 125,
  },
});

export default ProfileImageEditButton;
