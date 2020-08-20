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
    borderBottomEndRadius: 70,
    shadowColor: "rgba(27, 28, 32, 0.3)",
    shadowOffset: {
      width: 3,
      height: 20,
    },
    shadowRadius: 30,
    shadowOpacity: 1,
  },
  image: {
    height: 200,
  },
  raceInfoText: {
    padding: 20,
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
