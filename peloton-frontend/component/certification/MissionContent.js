import React from "react";
import { Text, View, StyleSheet } from "react-native";
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
    marginHorizontal: 10,
  },
  missionInstruction: {
    marginTop: 10,
    fontSize: 20,
    fontWeight: "bold",
  },
  missionTitle: {
    marginVertical: 10,
  },
  missionDetail: {
    marginTop: 10,
  },
  descriptionTitle: {
    fontSize: 14,
    fontWeight: "400",
  },
  description: {
    fontSize: 14,
    fontWeight: "200",
    color: COLOR.GRAY1,
  },
});

export default MissionContent;
