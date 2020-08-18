import React from "react";
import { StyleSheet, View } from "react-native";
import { Feather } from "@expo/vector-icons";

const CertificationStateIcon = ({
  icon,
  backgroundColor,
  iconColor = backgroundColor,
}) => {
  return (
    <View style={{ ...styles.centerAtParent, backgroundColor }}>
      <Feather name={icon} size={100} color={iconColor} />
    </View>
  );
};

const styles = StyleSheet.create({
  centerAtParent: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
    top: 0,
    justifyContent: "center",
    alignItems: "center",
    opacity: 0.8,
  },
});

export default CertificationStateIcon;
