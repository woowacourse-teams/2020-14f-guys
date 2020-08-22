import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";

const AchievementItem = ({ ratio, raceInitial, color }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.ratio}>{`${ratio}%`}</Text>
      <View style={styles.barContainer}>
        <View
          style={[
            styles.bar,
            { height: `${ratio}%`, backgroundColor: `${color}` },
          ]}
        />
      </View>
      <View style={[styles.raceNameContainer, { backgroundColor: `${color}` }]}>
        <Text style={styles.raceNameText}>{raceInitial}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    minHeight: 300,
    padding: 20,
    paddingTop: 10,
    alignItems: "center",
  },
  ratio: {
    padding: 10,
    fontSize: 12,
    color: COLOR.GRAY8,
    fontWeight: "500",
  },
  barContainer: {
    height: "60%",
    justifyContent: "flex-end",
    marginBottom: 20,
    padding: 5,
    backgroundColor: COLOR.WHITE,
    borderRadius: 20,
  },
  bar: {
    width: 25,
    opacity: 0.6,
    borderRadius: 15,
  },
  raceNameContainer: {
    borderRadius: 30,
  },
  raceNameText: {
    margin: 10,
    fontSize: 12,
    color: COLOR.WHITE,
    fontWeight: "700",
    textAlign: "center",
  },
});

export default AchievementItem;
