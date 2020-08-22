import { StyleSheet, Text, View } from "react-native";
import React from "react";
import { COLOR } from "../../../utils/constants";

const RaceSpecItem = ({ itemKey, value, border, keyStyle, valueStyle }) => {
  return (
    <View style={styles.container}>
      <View style={styles.itemBox}>
        <Text style={[styles.key, keyStyle]}>{itemKey}</Text>
        <Text style={[styles.value, valueStyle]}>{value}</Text>
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
    marginBottom: 15,
  },
  key: {
    fontSize: 12,
    lineHeight: 18,
    color: COLOR.GRAY8,
    flex: 4,
    marginBottom: 5,
  },
  value: {
    fontSize: 18,
    lineHeight: 21,
    color: COLOR.GRAY7,
    flex: 9,
  },
  border: {
    borderBottomWidth: 1,
    borderColor: COLOR.GRAY5,
    marginTop: 10,
    marginBottom: 25,
  },
});

export default RaceSpecItem;
