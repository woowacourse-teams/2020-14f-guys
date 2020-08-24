import React from "react";
import { StyleSheet, Text } from "react-native";

const RepositoryName = ({ children }) => {
  return <Text style={styles.container}>{children}</Text>;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    fontSize: 20,
    fontWeight: "400",
  },
});

export default RepositoryName;
