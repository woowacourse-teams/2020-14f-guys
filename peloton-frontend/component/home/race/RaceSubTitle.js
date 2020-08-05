import React from "react";
import { StyleSheet, Text } from "react-native";

const RaceSubTitle = ({ children }) => {
  return <Text style={styles.subtitle}>{children}</Text>;
};

const styles = StyleSheet.create({
  subtitle: {
    paddingLeft: 21,
    paddingTop: 23,
    color: "black",
    fontSize: 18,
    fontWeight: "bold",
    letterSpacing: 2,
    textAlign: "left",
  },
});

export default RaceSubTitle;
