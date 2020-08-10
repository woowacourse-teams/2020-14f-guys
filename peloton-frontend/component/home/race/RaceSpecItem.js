import { StyleSheet, Text, View } from "react-native";
import React from "react";
import { COLOR } from "../../../utils/constants";

const RaceSpecItem = ({ itemKey, value, border }) => {
  return (
    <View style={styles.container}>
      <View style={styles.itemBox}>
        <Text style={styles.key}>{itemKey}</Text>
        <Text style={styles.value}>{value}</Text>
      </View>
      {border && <View style={styles.border} />}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  itemBox: {
    flexDirection: "row",
    marginBottom: 15,
  },
  key: {
    fontSize: 18,
    lineHeight: 21,
    color: COLOR.BLACK,
    flex: 4,
  },
  value: {
    fontSize: 18,
    lineHeight: 21,
    color: COLOR.GRAY1,
    flex: 9,
  },
  border: {
    borderWidth: 1,
    borderColor: COLOR.GRAY5,
    marginTop: 10,
    marginBottom: 25,
  },
});

export default RaceSpecItem;
