import React from "react";
import { Text, View, StyleSheet } from "react-native";
import { COLOR } from "../../utils/constants";

const CertificationStatus = ({ status }) => {
  return (
    <View style={styles.status}>
      <Text style={styles.statusText}>{status}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  status: {
    paddingHorizontal: 5,
    paddingVertical: 6,
    borderRadius: 15,
    backgroundColor: COLOR.GREEN4,
    right: 10,
    bottom: 5,
    position: "absolute",
    alignSelf: "flex-start",
  },
  statusText: {
    fontSize: 12,
    color: COLOR.WHITE,
  },
});

export default CertificationStatus;
