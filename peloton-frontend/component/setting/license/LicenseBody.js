import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";

const LicenseBody = ({ children }) => {
  return (
    <View style={styles.container}>
      <Text>{children}</Text>
      <View style={styles.border} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  border: {
    marginVertical: 15,
    borderWidth: 2,
    borderColor: COLOR.GRAY4,
  },
});

export default LicenseBody;
