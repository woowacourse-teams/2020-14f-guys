import React from "react";
import { ScrollView, StyleSheet } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImage from "./RaceCertificationImage";
import RaceSpec from "./RaceSpec";

const RaceDetail = () => {
  return (
    <ScrollView style={styles.container}>
      <RaceCertificationImage />
      <RaceDetailInfo />
      <RaceSpec />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
});

export default RaceDetail;
