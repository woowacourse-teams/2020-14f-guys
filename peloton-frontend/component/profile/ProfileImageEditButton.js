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

const ProfileImageEditButton = ({ children, directUpdate }) => {
  const token = useRecoilValue(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const setIsLoading = useSetRecoilState(loadingState);

  const pickAndChangeProfileImage = async () => {
    setIsLoading(true);
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
        const formData = new FormData();
        formData.append("profile_image", {
          uri: profile,
          type: "image/jpeg",
          name: profile.substring(9),
        });
        await MemberApi.postProfile(token, formData);
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
