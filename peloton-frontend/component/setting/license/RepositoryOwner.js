import React from "react";
import { StyleSheet, Text } from "react-native";
import { COLOR } from "../../../utils/constants";

const RepositoryOwner = ({ children }) => {
  return <Text style={styles.container}>{`Copyright ${children}`}</Text>;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    color: COLOR.GRAY1,
  },
});

export default RepositoryOwner;
