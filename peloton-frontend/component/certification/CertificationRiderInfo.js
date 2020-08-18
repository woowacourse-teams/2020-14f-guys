import React from "react";
import { Text, View, StyleSheet } from "react-native";
import SmallProfileImage from "../profile/SmallProfileImage";
import { customDateTimeParser } from "../../utils/util";
import CertificationStatus from "./CertificationStatus";
import CertificationInfo from "./CertificationInfo";

const CertificationRiderInfo = ({ member, certification }) => {
  return (
    <View style={styles.container}>
      <SmallProfileImage member={member} />
      <CertificationInfo member={member} certification={certification} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: "flex-start",
    alignItems: "center",
    flexDirection: "row",
    marginHorizontal: 10,
    marginVertical: 15,
  },

});

export default CertificationRiderInfo;
