import React from "react";
import { ImageBackground, StyleSheet, View } from "react-native";
import MemberInfo from "./MemberInfo";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../../state/member/MemberState";

const ProfileDetail = () => {
  const memberInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.container}>
      <View style={styles.memberContainer}>
        <ImageBackground
          source={{ uri: memberInfo.profile }}
          defaultSource={require("../../../assets/default-image-background.png")}
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
    flex: 1,
    alignItems: "center",
    minHeight: 350,
  },
  background: {
    flex: 1,
    width: "100%",
  },
  raceContainer: {
    flex: 1,
    minHeight: 350,
  },
});

export default ProfileDetail;
