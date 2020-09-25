import React from "react";
import { StyleSheet, View } from "react-native";
import ProfileImageEditButton from "../ProfileImageEditButton";
import ProfileImage from "../ProfileImage";
import CameraIcon from "./CameraIcon";

const ProfileImageEdit = () => {
  return (
    <View style={styles.imageContainer}>
      <ProfileImageEditButton directUpdate={true}>
        <ProfileImage />
        <CameraIcon />
      </ProfileImageEditButton>
    </View>
  );
};
const styles = StyleSheet.create({
  imageContainer: {
    alignItems: "center",
    justifyContent: "center",
    marginTop: 25,
    marginBottom: 50,
    height: 150,
  },
});

export default ProfileImageEdit;
