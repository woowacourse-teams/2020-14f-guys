import React from "react";
import { Dimensions, StyleSheet, Text, TouchableOpacity } from "react-native";
import { COLOR } from "../../../utils/constants";

const FullWidthButton = ({ color, children, onClick }) => {
  return (
    <TouchableOpacity
      onPress={onClick}
      style={{ ...styles.container, backgroundColor: color }}
    >
      <Text style={styles.text}>{children}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    width: Dimensions.get("window").width,
    height: 55,
    justifyContent: "center",
    alignItems: "center",
  },
  text: {
    color: COLOR.WHITE,
    fontSize: 15,
    fontWeight: "600",
  },
});

export default FullWidthButton;
