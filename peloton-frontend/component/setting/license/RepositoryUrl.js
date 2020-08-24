import React from "react";
import { LinkingStatic as Linking, StyleSheet, Text } from "react-native";
import { COLOR } from "../../../utils/constants";

const RepositoryUrl = ({ children }) => {
  return (
    <Text
      style={styles.container}
      onPress={() => Linking.openURL(`${children}`)}
    >
      {children}
    </Text>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    color: COLOR.BLUE2,
  },
});

export default RepositoryUrl;
