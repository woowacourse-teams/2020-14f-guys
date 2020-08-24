import React from "react";
import { StyleSheet, Text } from "react-native";

const LicenseTitle = ({ children }) => {
  return <Text style={styles.container}>{children}</Text>;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    fontSize: 30,
  },
});

export default LicenseTitle;
