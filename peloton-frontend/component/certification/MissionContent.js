import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { customTimeParser } from "../../utils/util";
import { COLOR } from "../../utils/constants";

const MissionContent = ({ mission }) => {
  return (
    <View style={styles.container}>
      <View style={styles.durationContainer}>
        <Text style={styles.duration}>
          {customTimeParser(mission.mission_duration.start_time)}
        </Text>
        <Text style={styles.durationText}>{" 에서  "}</Text>
        <Text style={styles.duration}>
          {customTimeParser(mission.mission_duration.end_time)}
        </Text>
        <Text style={styles.durationText}>{" 사이에  "}</Text>
      </View>
      <View style={styles.instructionContainer}>
        <Text style={styles.instruction}>{mission.mission_instruction}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginHorizontal: 40,
    marginTop: 50,
  },
  durationContainer: {
    flexDirection: "row",
    paddingLeft: 7,
    alignItems: "flex-end",
    paddingBottom: 5,
  },
  duration: {
    fontSize: 14,
    fontWeight: "500",
    color: COLOR.GRAY7,
    marginBottom: 5,
  },
  durationText: {
    fontSize: 12,
    fontWeight: "400",
    color: COLOR.GRAY8,
    marginBottom: 5,
  },
  instruction: {
    textAlign: "center",
    fontSize: 22,
    lineHeight: 30,
    fontWeight: "700",
    color: COLOR.DARK_GRAY6,
  },
});

export default MissionContent;
