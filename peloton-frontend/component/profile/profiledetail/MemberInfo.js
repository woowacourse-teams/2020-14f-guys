import React from "react";
import { StyleSheet, View } from "react-native";
import ProfileImage from "../ProfileImage";
import MemberInfoDetail from "./MemberInfoDetail";
import CustomButton from "../CustomButton";
import { useNavigation } from "@react-navigation/core";
import ProfileDefaultImage from "../ProfileDefaultImage";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../../state/member/MemberState";

const MemberInfo = () => {
  const navigation = useNavigation();
  const userInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.memberInfo}>
      <View style={styles.imageContainer}>
        {userInfo.profile ? <ProfileImage image={userInfo.profile}/> : <ProfileDefaultImage/>}
      </View>
      <MemberInfoDetail name={userInfo.name} cash={userInfo.cash}/>
      <CustomButton
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
