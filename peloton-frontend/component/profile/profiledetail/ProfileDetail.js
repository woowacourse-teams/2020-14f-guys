import React from "react";
import { ImageBackground, StyleSheet, View } from "react-native";
import MemberInfo from "./MemberInfo";
import { useRecoilValue } from "recoil";
import { userInfoState } from "../../atoms";

const ProfileDetail = () => {
  const userInfo = useRecoilValue(userInfoState);

  return (
    <View style={styles.container}>
      <View style={styles.memberContainer}>
        <ImageBackground
          source={{ url: userInfo.profile.baseImageUrl }}
          style={styles.background}
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
    backgroundColor: "red",
    alignItems: "center",
  },
  background: {
    flex: 1,
    width: "100%",
  },
  raceContainer: {
    flex: 11,
  },
});

export default ProfileDetail;
