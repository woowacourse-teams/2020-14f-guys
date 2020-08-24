import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";

const AchievementItem = ({ achievement, raceTitle, color }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.ratio}>{`${achievement}%`}</Text>
      <View style={styles.barContainer}>
        <View
          style={[
            styles.bar,
            {
              opacity: achievement * 0.01 > 0.5 ? achievement * 0.01 : 0.5,
              height: `${achievement}%`,
              backgroundColor: COLOR.PURPLE,
            },
          ]}
        />
      </View>
      <Text style={styles.raceNameText}>{raceTitle}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    minWidth: 90,
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
  raceNameText: {
    margin: 10,
    fontSize: 12,
    color: COLOR.BLACK,
    fontWeight: "700",
    textAlign: "center",
  },
});

export default AchievementItem;
