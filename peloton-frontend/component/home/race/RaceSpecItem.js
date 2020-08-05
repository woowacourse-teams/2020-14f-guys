import { StyleSheet, Text, View } from "react-native";
import React from "react";
import { COLOR } from "../../../utils/constants";

const RaceSpecItem = ({ itemKey, value }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.key}>{itemKey}</Text>
      <Text style={styles.value}>{value}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    flex: 1,
    marginBottom: 15,
  },
  key: {
    fontSize: 18,
    lineHeight: 21,
    color: COLOR.BLACK,
    textAlign: "left",
    flex: 4,
  },
  value: {
    fontSize: 18,
    lineHeight: 21,
    color: COLOR.GRAY1,
    textAlign: "left",
    flex: 9,
  },
});

export default RaceSpecItem;
