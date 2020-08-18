import React from "react";
import { Text, View, StyleSheet } from "react-native";
import { COLOR } from "../../utils/constants";

const CertificationStatus = ({ certification }) => {
  return (
    <View style={styles.status}>
      <Text style={styles.statusText}>{certification.status}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  status: {
    width: 65,
    height: 35,
    borderRadius: 18,
    backgroundColor: COLOR.GREEN4,
    justifyContent: "center",
    alignItems: "center",
    right: 10,
    bottom: 3,
    position: "absolute",
  },
  statusText: {
    fontSize: 12,
    color: COLOR.WHITE,
  },
});

export default CertificationStatus;
