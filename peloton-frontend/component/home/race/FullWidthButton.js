import React from "react";
import { Dimensions, StyleSheet, Text, TouchableOpacity } from "react-native";
import { COLOR } from "../../../utils/constants";

const FullWidthButton = ({ color, children, onClick }) => {
  return (
    <TouchableOpacity
      onPress={onClick}
      style={{ ...styles.paymentButton, backgroundColor: color }}
    >
      <Text style={styles.paymentText}>{children}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  paymentButton: {
    width: Dimensions.get("window").width,
    height: 55,
    justifyContent: "center",
    alignItems: "center",
  },
  paymentText: {
    color: COLOR.WHITE,
    fontSize: 15,
    fontWeight: "600",
  },
});

export default FullWidthButton;
