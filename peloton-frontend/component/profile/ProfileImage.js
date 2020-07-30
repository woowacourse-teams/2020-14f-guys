import React from "react";
import { Image, StyleSheet } from "react-native";

const ProfileImage = ({ image }) => {
  return <Image style={styles.profileImage} source={{ url: image }} />;
};

const styles = StyleSheet.create({
  profileImage: {
    resizeMode: "cover",
    width: 100,
    height: 100,
    borderRadius: 100 / 2,
  },
});

export default ProfileImage;
