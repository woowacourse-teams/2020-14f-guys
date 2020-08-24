import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";

const CalculationResult = ({ achievementRate }) => {
  const rate = achievementRate.achievement;

  return (
    <View style={styles.container}>
      <View style={styles.top}>
        <Text style={styles.riderName}>{achievementRate.member_name}</Text>
        <Text style={styles.prize}>{`${achievementRate.prize}원`}</Text>
      </View>
      <View style={styles.bottom}>
        <View style={styles.barContainer}>
          <View style={[styles.successRatioBar, { width: `${rate}%` }]} />
        </View>
        <Text style={styles.successRatioText}>{`${rate}%`}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    minHeight: 50,
    maxHeight: 80,
    marginBottom: 40,
  },
  top: {
    flex: 1,
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 5,
  },
  riderName: {
    flex: 1,
    minWidth: 50,
    fontSize: 18,
    fontWeight: "600",
  },
  prize: {
    flex: 3,
    fontSize: 18,
    textAlign: "right",
    color: COLOR.DARK_GRAY6,
  },
  bottom: {
    flex: 2,
    flexDirection: "row",
    marginTop: 10,
  },
  barContainer: {
    width: "85%",
    marginBottom: 20,
    padding: 5,
    backgroundColor: COLOR.WHITE,
    borderRadius: 20,
  },
  successRatioBar: {
    height: "100%",
    fontSize: 20,
    backgroundColor: COLOR.PURPLE,
    borderRadius: 120,
  },
  successRatioText: {
    fontSize: 14,
    fontWeight: "400",
    color: COLOR.DARK_GRAY6,
    marginLeft: 10,
  },
});

export default CalculationResult;
