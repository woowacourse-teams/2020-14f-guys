import React from "react";
import { Image, StyleSheet } from "react-native";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../state/member/MemberState";

const ProfileImage = () => {
  const memberInfo = useRecoilValue(memberInfoState);

  return (
    <Image
      style={styles.profileImage}
      source={
        memberInfo
          ? { uri: memberInfo.profile }
          : require("../../assets/default-profile.jpg")
      }
      defaultSource={require("../../assets/default-profile.jpg")}
    />
  );
};

const styles = StyleSheet.create({
  profileImage: {
    resizeMode: "cover",
    width: 125,
    height: 125,
    borderRadius: 63,
  },
});

export default ProfileImage;
