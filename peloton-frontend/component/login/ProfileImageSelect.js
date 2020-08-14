import React from "react";
import { Image, StyleSheet, View } from "react-native";
import ProfileImageEditButton from "../profile/ProfileImageEditButton";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../state/member/MemberState";
import CameraIcon from "../profile/profileedit/CameraIcon";

const ProfileImageSelect = () => {
  const memberInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.container}>
      <ProfileImageEditButton>
        <Image
          style={styles.profileImage}
          source={
            memberInfo.profile
              ? { uri: memberInfo.profile }
              : require("../../assets/default-profile.jpg")
          }
          defaultSource={require("../../assets/default-profile.jpg")}
        />
        <CameraIcon />
      </ProfileImageEditButton>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    paddingBottom: 130,
  },
  profileImage: {
    height: 120,
    width: 120,
    borderRadius: 60,
  },
});

export default ProfileImageSelect;
