import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";
import ReadMore from "../../../utils/ReadMore";

const RaceDetailInfo = ({ title, description }) => {
  return (
    <View style={styles.container}>
      <View style={styles.raceInfoText}>
        <Text style={styles.title}>{title}</Text>
        <ReadMore>{description}</ReadMore>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    overflow: "hidden",
  },
  image: {
    height: 200,
  },
  raceInfoText: {
    paddingHorizontal: 30,
    paddingTop: 25,
    backgroundColor: COLOR.WHITE,
  },
  title: {
    color: COLOR.BLACK,
    fontWeight: "600",
    fontSize: 25,
    justifyContent: "center",
    paddingBottom: 10,
  },
});

export default RaceDetailInfo;
