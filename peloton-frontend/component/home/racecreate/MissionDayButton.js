import React from "react";
import { StyleSheet, Text, TouchableOpacity } from "react-native";
import { COLOR } from "../../../utils/constants";

const MissionDayButton = ({ day, toggleDay, clicked }) => {
  const color = clicked ? COLOR.GREEN3 : COLOR.GREEN1;

  return (
    <TouchableOpacity
      onPress={toggleDay}
      activeOpacity={0.5}
      style={{ ...styles.container, backgroundColor: color, opacity: 1 }}
    >
      <Text style={{ color: COLOR.GRAY1 }}>{day.substring(0, 3)}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: COLOR.GREEN1,
    borderRadius: 40,
    width: 55,
    height: 55,
    marginHorizontal: 10,
    marginBottom: 20,
    overflow: "hidden",
    justifyContent: "center",
    alignItems: "center",
  },
});

export default MissionDayButton;
