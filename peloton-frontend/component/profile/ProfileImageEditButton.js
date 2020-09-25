import React from "react";
import { Alert, StyleSheet, TouchableOpacity } from "react-native";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import * as ImagePicker from "expo-image-picker";

import { MemberApi } from "../../utils/api/MemberApi";
import { memberInfoState, memberTokenState, } from "../../state/member/MemberState";
import { loadingState } from "../../state/loading/LoadingState";
import { getCameraRollPermission } from "../../utils/Permission";
import { logNav } from "../../utils/Analytics";

const ProfileImageEditButton = ({ children, directUpdate }) => {
  const token = useRecoilValue(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const setIsLoading = useSetRecoilState(loadingState);

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
      Alert.alert("", error.response.data.code);
      console.log(error.response.data.message);
    }
    setIsLoading(false);
  };

  const pickAndChangeProfileImage = async () => {
    setIsLoading(true);
    logNav("Profile", "ProfileImageUpdate");
    const hasCameraRollPermission = await getCameraRollPermission();
    if (hasCameraRollPermission === false) {
      setIsLoading(false);
      return;
    } else {
      const pickerResult = await ImagePicker.launchImageLibraryAsync({
        allowsEditing: true,
        aspect: [4, 3],
        quality: 0.1,
      });
      if (pickerResult.cancelled === true) {
        console.log("cameraroll picker cancelled");
        setIsLoading(false);
        return;
      }
      const profile = pickerResult.uri;
      setMemberInfo({
        ...memberInfo,
        profile,
      });
      if (directUpdate) {
        await requestChangeImage(profile);
      }
    }
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
