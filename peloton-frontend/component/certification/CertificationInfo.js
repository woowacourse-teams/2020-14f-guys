import React from "react";
import { Text, View, StyleSheet } from "react-native";
import { customDateTimeParser } from "../../utils/util";
import CertificationStatus from "./CertificationStatus";
import CertificationCreatedAt from "./CertificationCreatedAt";

const CertificationInfo = ({ memberName, certification }) => {
  return (
    <View style={styles.container}>
      <View style={styles.memberProfileTextContainer}>
        <Text style={styles.memberProfileText}>{memberName}</Text>
        <CertificationCreatedAt createdAt={certification.created_at} />
      </View>
      <CertificationStatus status={certification.status} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  memberProfileTextContainer: {
    justifyContent: "space-between",
  },
  memberProfileText: {
    fontSize: 16,
    fontWeight: "bold",
    marginVertical: 3,
  },
});

export default CertificationInfo;
