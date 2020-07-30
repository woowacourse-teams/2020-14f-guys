import React from "react";
import ProfileImage from "../ProfileImage";
import { Image, StyleSheet, TouchableOpacity, View } from "react-native";
import { useRecoilValue } from "recoil";
import { userInfoState } from "../../atoms";

const ProfileEditImage = () => {
  const userInfo = useRecoilValue(userInfoState);

  return (
    <View style={styles.imageContainer}>
      <ProfileImage image={userInfo.profile.baseImageUrl} />
      <TouchableOpacity>
        <Image
          style={styles.profileEditButton}
          source={require("../../../assets/icn_edit.png")}
        />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#eceff0",
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

export default ProfileEditImage;
