import React from "react";
import { StyleSheet, View } from "react-native";
import ProfileImage from "../ProfileImage";
import MemberInfoDetail from "./MemberInfoDetail";
import ProfileEditButton from "../ProfileEditButton";
import { useNavigation } from "@react-navigation/core";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../../state/member/MemberState";

const MemberInfo = () => {
  const navigation = useNavigation();
  const memberInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.memberInfo}>
      <View style={styles.imageContainer}>
        <ProfileImage image={memberInfo.profile} />
      </View>
      <MemberInfoDetail name={memberInfo.name} cash={memberInfo.cash} />
      <ProfileEditButton
        text="Edit Profile"
        onPress={() => navigation.navigate("ProfileEdit")}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  memberInfo: {
    flex: 1,
    alignItems: "center",
    backgroundColor: "rgba(0, 0, 0, 0.3)",
  },
  imageContainer: {
    height: 125,
    marginTop: 35,
  },
});

export default MemberInfo;
