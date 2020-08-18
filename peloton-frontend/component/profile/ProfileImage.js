import React from "react";
import { Image, StyleSheet, View } from "react-native";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../state/member/MemberState";

const ProfileImage = () => {
  const memberInfo = useRecoilValue(memberInfoState);
  return (
    <View style={styles.container}>
      <Image
        style={styles.profileImage}
        source={{ uri: memberInfo.profile }}
        defaultSource={require("../../assets/default-profile.jpg")}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  profileImage: {
    resizeMode: "cover",
    width: 100,
    height: 100,
    borderRadius: 100 / 2,
  },
  profileEditButton: {
    width: 30,
    height: 30,
    position: "absolute",
    left: 30,
    bottom: 30,
  },
});

export default ProfileImage;
