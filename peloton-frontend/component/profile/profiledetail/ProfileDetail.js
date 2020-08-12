import React from "react";
import { ImageBackground, StyleSheet, View } from "react-native";
import MemberInfo from "./MemberInfo";
import { useRecoilValue } from "recoil";
import { COLOR } from "../../../utils/constants";
import { memberInfoState } from "../../../state/member/MemberState";

const ProfileDetail = () => {
  const userInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.container}>
      <View style={styles.memberContainer}>
        <ImageBackground
          source={
            userInfo.profile
              ? { url: userInfo.profile }
              : require("../../../assets/default-profile.jpg")
          }
          style={styles.background}
          blurRadius={6}
        >
          <MemberInfo />
        </ImageBackground>
      </View>
      <View style={styles.raceContainer} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  memberContainer: {
    flex: 9,
    backgroundColor: COLOR.RED,
    alignItems: "center",
  },
  background: {
    flex: 1,
    width: "120%",
  },
  raceContainer: {
    flex: 11,
  },
});

export default ProfileDetail;
