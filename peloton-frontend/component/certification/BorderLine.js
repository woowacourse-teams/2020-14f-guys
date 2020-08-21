import React from "react";
import { Text, View, StyleSheet } from "react-native";
import { COLOR, deviceWidth } from "../../utils/constants";

const BorderLine = () => {
  return <View style={styles.border} />;
};

const styles = StyleSheet.create({
  border: {
    alignSelf: "center",
    width: "100%",
    borderBottomWidth: 1,
    borderColor: COLOR.GRAY3,
    marginBottom: 5,
  },
});

export default BorderLine;
