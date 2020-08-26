import React from "react";
import { Dimensions, StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";

const CalculationResult = ({ achievementRate }) => {
  const { member_name, prize, achievement: rate } = achievementRate;

  return (
    achievementRate && (
      <View style={styles.container}>
        <View style={styles.top}>
          <Text style={styles.riderName}>{member_name}</Text>
          <Text style={styles.prize}>{`${prize}원`}</Text>
        </View>
        <View style={styles.bottom}>
          <View style={styles.barContainer}>
            <View
              style={[styles.successRatioBar, { width: rate ? `${rate}%` : 0 }]}
            />
          </View>
          <View style={styles.ratioTextContainer}>
            <Text style={styles.successRatioText}>{`${rate}%`}</Text>
          </View>
        </View>
      </View>
    )
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    height: 75,
    marginVertical: 20,
  },
  top: {
    height: 20,
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
    height: 30,
    flexDirection: "row",
    marginTop: 10,
  },
  barContainer: {
    width: "85%",
    height: 20,
    marginBottom: 20,
    padding: 5,
    backgroundColor: COLOR.WHITE,
    borderRadius: 20,
  },
  successRatioBar: {
    height: 10,
    fontSize: 20,
    backgroundColor: COLOR.PURPLE,
    borderRadius: 120,
  },
  ratioTextContainer: {
    width: "15%",
  },
  successRatioText: {
    fontSize: 15,
    textAlign: "right",
    fontWeight: "400",
    color: COLOR.DARK_GRAY6,
    marginLeft: 10,
  },
});

export default CalculationResult;
