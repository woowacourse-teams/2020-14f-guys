import React from "react";
import { StyleSheet, Text } from "react-native";

const RepositoryLicense = ({ children }) => {
  return <Text style={styles.container}>{children}</Text>;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginBottom: 20,
  },
});

export default RepositoryLicense;
