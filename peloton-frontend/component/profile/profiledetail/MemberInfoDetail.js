import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";

const MemberInfoDetail = ({ name, cash }) => {
  return (
    <View style={styles.textContainer}>
      <Text style={styles.nickname}>{name}</Text>
      <Text style={styles.cash}>💰{cash}</Text>
    </View>
  );
};

export default MemberInfoDetail;
const styles = StyleSheet.create({
  textContainer: {
    height: 65,
    marginVertical: 15,
    alignItems: "center",
    borderRadius: 25,
    padding: 10,
  },
  nickname: {
    height: 30,
    marginBottom: 2,
    fontSize: 18,
    color: COLOR.WHITE2,
    fontWeight: "bold",
  },
  cash: {
    height: 20,
    fontSize: 15,
    color: COLOR.WHITE2,
    fontWeight: "300",
  },
});
