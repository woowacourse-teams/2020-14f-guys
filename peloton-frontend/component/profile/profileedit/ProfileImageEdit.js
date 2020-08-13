import React from "react";
import { StyleSheet, View } from "react-native";
import ProfileImageEditButton from "../ProfileImageEditButton";
import ProfileImage from "../ProfileImage";
import ProfileImageEditIcon from "./ProfileImageEditIcon";

const ProfileImageEdit = () => {
  return (
    <View style={styles.imageContainer}>
      <ProfileImageEditButton>
        <ProfileImage />
        <ProfileImageEditIcon />
      </ProfileImageEditButton>
    </View>
  );
};
const styles = StyleSheet.create({
  imageContainer: {
    alignItems: "center",
    justifyContent: "center",
    marginTop: 25,
    height: 150,
  },
});

export default ProfileImageEdit;
