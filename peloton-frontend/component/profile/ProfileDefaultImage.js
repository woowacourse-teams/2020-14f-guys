import { Image, StyleSheet } from "react-native";
import React from "react";

const ProfileDefaultImage = () => {
  return (
    <Image
      style={styles.profileImage}
      source={require("../../assets/noru.jpeg")}
    />
  );
};

const styles = StyleSheet.create({
  profileImage: {
    resizeMode: "cover",
    width: 100,
    height: 100,
    borderRadius: 100 / 2,
  },
});

export default ProfileDefaultImage;
