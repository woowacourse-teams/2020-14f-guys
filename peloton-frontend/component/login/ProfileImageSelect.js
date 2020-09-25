import React from "react";
import { StyleSheet, View } from "react-native";
import ProfileImageEditButton from "../profile/ProfileImageEditButton";
import CameraIcon from "../profile/profileedit/CameraIcon";
import ProfileImage from "../profile/ProfileImage";

const ProfileImageSelect = () => {
  return (
    <View style={styles.container}>
      <ProfileImageEditButton directUpdate={false}>
        <ProfileImage />
        <CameraIcon />
      </ProfileImageEditButton>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    paddingBottom: 130,
    minHeight: 120,
  },
});

export default ProfileImageSelect;
