import React from "react";
import { StyleSheet, Text, View } from "react-native";

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
  nickname: {
    flex: 1,
    marginBottom: 7,

    fontSize: 18,
    color: "#F2F2F2",
    fontWeight: "bold",
  },
  cash: {
    flex: 1,
    fontSize: 15,
    color: "#F2F2F2",
    fontWeight: "300",
  },
  textContainer: {
    flex: 2.5,
    marginTop: 10,
    marginBottom: 15,
    alignItems: "center",
  },
});
