import React from "react";
import { Image, StyleSheet, View } from "react-native";
import ProfileImageEditButton from "../profile/ProfileImageEditButton";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../state/member/MemberState";
import ProfileImageEditIcon from "../profile/profileedit/ProfileImageEditIcon";

const ProfileImageSelect = () => {
  const userInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.container}>
      <ProfileImageEditButton>
        <Image
          style={styles.profileImage}
          source={
            userInfo.profile
              ? { uri: userInfo.profile }
              : require("../../assets/default-profile.jpg")
          }
          defaultSource={require("../../assets/default-profile.jpg")}
        />
        <ProfileImageEditIcon />
      </ProfileImageEditButton>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 250,
  },
  profileImage: {
    height: 120,
    width: 120,
    borderRadius: 60,
  },
  cameraIcon: {
    opacity: 0.7,
    alignItems: "center",
    justifyContent: "center",
    width: 30,
    height: 30,
    borderRadius: 15,
    backgroundColor: "white",
    position: "absolute",
    top: 85,
    left: 85,
  },
});

export default ProfileImageSelect;
