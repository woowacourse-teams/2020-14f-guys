import React from "react";
import { View, StyleSheet } from "react-native";
import SmallProfileImage from "../profile/SmallProfileImage";
import CertificationInfo from "./CertificationInfo";

const CertificationRiderInfo = ({ member, certification }) => {
  return (
    <View style={styles.container}>
      <SmallProfileImage uri={member.profile} />
      <CertificationInfo
        memberName={member.name}
        certification={certification}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    flexDirection: "row",
    marginVertical: 15,
  },
});

export default CertificationRiderInfo;
