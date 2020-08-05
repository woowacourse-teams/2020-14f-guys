import React from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImage from "./RaceCertificationImage";
import RaceSpec from "./RaceSpec";

const RaceDetail = () => {
  return (
    <ScrollView style={styles.container}>
      <View style={{ flex: 3 }}>
        <RaceCertificationImage />
      </View>
      <View style={{ flex: 1 }}>
        <RaceDetailInfo />
      </View>
      <View style={{ flex: 1 }}>
        <RaceSpec />
      </View>
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
