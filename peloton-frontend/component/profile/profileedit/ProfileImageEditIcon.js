import React from "react";
import { StyleSheet, View } from "react-native";
import { Entypo } from "@expo/vector-icons";
import { COLOR } from "../../../utils/constants";

const ProfileImageEditIcon = () => {
  return (
    <View style={styles.cameraIcon}>
      <Entypo name="camera" size={24} color="black" />
    </View>
  );
};

const styles = StyleSheet.create({
  cameraIcon: {
    opacity: 0.7,
    alignItems: "center",
    justifyContent: "center",
    width: 30,
    height: 30,
    borderRadius: 15,
    backgroundColor: COLOR.WHITE,
    position: "absolute",
    top: 70,
    left: 70,
  },
});

export default ProfileImageEditIcon;
