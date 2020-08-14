import React from "react";
import { StyleSheet, View } from "react-native";
import { Entypo } from "@expo/vector-icons";
import { COLOR } from "../../../utils/constants";

const CameraIcon = () => {
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
    marginTop: 90,
    alignSelf: "flex-end",
  },
});

export default CameraIcon;
