import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { customTimeParser } from "../../utils/util";
import { COLOR } from "../../utils/constants";

const MissionContent = ({ mission }) => {
  return (
    <View style={styles.container}>
      <View style={styles.missionTitle}>
        <Text style={styles.missionInstruction}>
          {mission.mission_instruction}
        </Text>
      </View>
      <View style={styles.missionDetail}>
        <Text style={styles.descriptionTitle}>인증 가능 시간</Text>
        <Text style={styles.description}>
          {customTimeParser(mission.mission_duration.start_time)} {"~ "}
          {customTimeParser(mission.mission_duration.end_time)}
        </Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginHorizontal: 20,
  },
  missionInstructionTitle: {
    fontSize: 16,
    fontWeight: "500",
    color: COLOR.DARK_GRAY6,
    marginBottom: 5,
  },
  missionInstruction: {
    padding: 20,
    textAlign: "center",
    fontSize: 20,
    lineHeight: 30,
    fontWeight: "700",
    color: COLOR.DARK_GRAY6,
  },
  missionTitle: {
    marginVertical: 10,
  },
  missionDetail: {
    marginTop: 10,
  },
  descriptionTitle: {
    fontSize: 12,
    fontWeight: "400",
    color: COLOR.GRAY8,
    marginBottom: 5,
  },
  description: {
    fontSize: 18,
    fontWeight: "400",
    color: COLOR.GRAY7,
  },
});

export default MissionContent;
