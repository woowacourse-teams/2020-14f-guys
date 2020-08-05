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
    backgroundColor: "white",
    borderBottomEndRadius: 15,
    borderBottomStartRadius: 15,
  },
  image: {
    height: 200,
  },
  achievement: {
    position: "absolute",
    left: 30,
    top: 30,
  },
  duration: {
    position: "absolute",
    color: COLOR.BLACK,
    right: 30,
    top: 30,
  },
  raceInfoText: {
    paddingHorizontal: 20,
    paddingVertical: 20,
    backgroundColor: COLOR.WHITE,
  },
  title: {
    color: COLOR.BLACK,
    fontWeight: "900",
    fontSize: 17,
    justifyContent: "center",
    paddingBottom: 10,
  },
});

export default RaceDetailInfo;
