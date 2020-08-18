import React from "react";
import { Text, View, StyleSheet } from "react-native";
import { customDateTimeParser } from "../../utils/util";
import CertificationStatus from "./CertificationStatus";

const CertificationInfo = ({ member, certification }) => {
  return (
    <View style={styles.container}>
      <View style={styles.memberProfileTextContainer}>
        <Text style={styles.memberProfileText}>{member.name}</Text>
        <Text style={styles.certificationDateText}>
          {customDateTimeParser(certification.created_at)}
        </Text>
      </View>
      <CertificationStatus certification={certification} />
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
  certificationDateText: {
    fontWeight: "200",
    marginVertical: 3,
  },
});

export default CertificationInfo;
