import React from "react";
import { StyleSheet, View } from "react-native";
import ProfileImage from "../ProfileImage";
import MemberInfoDetail from "./MemberInfoDetail";
import ProfileEditButton from "../ProfileEditButton";
import { useNavigation } from "@react-navigation/core";
import ProfileDefaultImage from "../ProfileDefaultImage";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../../state/member/MemberState";

const MemberInfo = () => {
  const navigation = useNavigation();
  const memberInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.memberInfo}>
      <View style={styles.imageContainer}>
        {memberInfo.profile ? (
          <ProfileImage image={memberInfo.profile} />
        ) : (
          <ProfileDefaultImage />
        )}
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
    width: "auto",
    alignItems: "center",
  },
  imageContainer: {
    flex: 5,
    marginTop: 45,
    marginBottom: 20,
  },
});

export default MemberInfo;
