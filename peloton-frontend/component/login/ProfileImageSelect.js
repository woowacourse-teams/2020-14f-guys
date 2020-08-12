import React from "react";
import { Image, StyleSheet, View } from "react-native";
import { Entypo } from "@expo/vector-icons";
import ProfileImageEditButton from "../profile/ProfileImageEditButton";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../state/member/MemberState";

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
        />
        <View style={styles.cameraIcon}>
          <Entypo name="camera" size={24} color="black" />
        </View>
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
