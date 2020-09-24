import React from "react";
import { StyleSheet, View } from "react-native";
import moment from "moment";

import RaceSpecItem from "./RaceSpecItem";
import { COLOR, DAYS_IN_KOREAN } from "../../../utils/constants";

const RaceSpec = ({
  days,
  raceDuration,
  missionDuration,
  cash,
  riderCount,
}) => {
  const startDateTime = moment
    .utc(
      `${raceDuration.start_date} ${missionDuration.start_time}`,
      "YYYY-MM-DD HH:mm:ss"
    )
    .local();
  const endDateTime = moment
    .utc(
      `${raceDuration.end_date}T${missionDuration.end_time}`,
      "YYYY-MM-DD HH:mm:ss"
    )
    .local();
  const startDate = startDateTime.format("YYYY-MM-DD");
  const endDate = endDateTime.format("YYYY-MM-DD");
  const startTime = startDateTime.format("HH:mm");
  const endTime = endDateTime.format("HH:mm");

  return (
    <View style={styles.container}>
      <RaceSpecItem
        itemKey={"레이스 기간"}
        value={`${startDate} ~ ${endDate}`}
        border
      />
      <RaceSpecItem
        itemKey={"인증 주기"}
        value={days ? days.map((day) => DAYS_IN_KOREAN[day]).join(", ") : ""}
        border
      />
      <RaceSpecItem
        itemKey={"인증 시간"}
        value={`${startTime} ~ ${endTime}`}
        border
      />
      <RaceSpecItem
        itemKey={"모인 금액"}
        value={`${cash * riderCount}원`}
        border
      />
      <RaceSpecItem
        itemKey={"참가자 수"}
        value={`${riderCount}명`}
        border={false}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 30,
    paddingTop: 40,
    margin: 20,
    borderWidth: 1,
    borderColor: COLOR.GRAY5,
    borderRadius: 20,
  },
  border: {
    borderWidth: 1,
    borderColor: COLOR.GRAY5,
    marginTop: 10,
    marginBottom: 25,
  },
});

export default RaceSpec;
