import React from "react";
import ProfileImage from "../ProfileImage";
import { Image, StyleSheet, View } from "react-native";
import ProfileImageEditButton from "../ProfileImageEditButton";
import { useRecoilValue } from "recoil";
import { COLOR } from "../../../utils/constants";
import ProfileDefaultImage from "../ProfileDefaultImage";
import { memberInfoState } from "../../../state/member/MemberState";

const ProfileImageEdit = () => {
  const userInfo = useRecoilValue(memberInfoState);

  return (
    <View style={styles.imageContainer}>
      {userInfo.profile? <ProfileImage image={userInfo.profile}/> : <ProfileDefaultImage/>}
      <ProfileImageEditButton>
        <Image
          style={styles.profileEditButton}
          source={require("../../../assets/icn_edit.png")}
        />
      </ProfileImageEditButton>
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE4,
  },
  imageContainer: {
    alignItems: "center",
    justifyContent: "center",
    marginTop: 25,
  },
  profileEditButton: {
    width: 40,
    height: 40,
    left: 30,
    bottom: 30,
  },
});

export default ProfileImageEdit;
