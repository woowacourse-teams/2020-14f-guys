import React from "react";
import { Text, StyleSheet } from "react-native";

const RaceSubTitle = ({ children }) => {
  return <Text style={styles.subtitle}>{children}</Text>;
};

const styles = StyleSheet.create({
  subtitle: {
    paddingTop: 5,
    paddingBottom: 20,
    color: "black",
    fontSize: 17,
    fontWeight: "900",
    letterSpacing: 2,
    textAlign: "center",
  },
});

export default RaceSubTitle;
