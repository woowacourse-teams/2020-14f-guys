import React from "react";
import { StyleSheet, TouchableOpacity } from "react-native";
import { FontAwesome } from "@expo/vector-icons";
import { COLOR } from "../../../utils/constants";

const CalendarButton = ({ showCalendar }) => {
  return (
    <TouchableOpacity
      activeOpacity={0.3}
      style={styles.container}
      onPress={showCalendar}
    >
      <FontAwesome name="calendar" size={24} color="black" />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: COLOR.GREEN,
    borderRadius: 20,
    marginLeft: 10,
    width: 44,
    height: 44,
    overflow: "hidden",
    justifyContent: "center",
    alignItems: "center",
  },
});

export default CalendarButton;
