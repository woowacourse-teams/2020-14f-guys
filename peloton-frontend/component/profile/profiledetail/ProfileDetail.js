import React, { useEffect } from "react";
import { ImageBackground, StyleSheet, View } from "react-native";
import MemberInfo from "./MemberInfo";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../../state/member/MemberState";
import AchievementItems from "./AchievementItems";
import { logNav } from "../../../utils/Analytics";

const ProfileDetail = () => {
  const memberInfo = useRecoilValue(memberInfoState);

  useEffect(() => logNav("Profile", "ProfileHome"), []);

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
      <View style={styles.achievementContainer}>
        <AchievementItems />
      </View>
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
  achievementContainer: {
    flex: 11,
  },
});

export default ProfileDetail;
