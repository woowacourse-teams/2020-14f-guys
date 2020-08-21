import React from "react";
import { Text, View, StyleSheet } from "react-native";
import { customTimeParser } from "../../utils/util";
import { COLOR } from "../../utils/constants";

const CertificationCreatedAt = ({ createdAt }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.timeText}>{customTimeParser(createdAt)}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    marginVertical: 3,
  },
  timeText: {
    fontWeight: "400",
    color: COLOR.GRAY2,
  },
});

export default CertificationCreatedAt;
