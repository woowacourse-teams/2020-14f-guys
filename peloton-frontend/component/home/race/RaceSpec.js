import React from "react";
import { StyleSheet, View } from "react-native";
import RaceSpecItem from "./RaceSpecItem";
import { COLOR } from "../../../utils/constants";

const RaceSpec = ({
  days,
  raceDuration,
  missionDuration,
  cash,
  riderCount,
}) => {
  const startDate = raceDuration.start_date;
  const endDate = raceDuration.end_date;
  const startTime = missionDuration.start_time;
  const endTime = missionDuration.end_time;
  return (
    <View style={styles.container}>
      <RaceSpecItem
        itemKey={"레이스 기간"}
        value={`${startDate} ~ ${endDate}`}
        border
      />
      <RaceSpecItem
        itemKey={"인증 주기"}
        value={days ? days.join(", ") : ""}
        border
      />
      <RaceSpecItem
        itemKey={"인증 시간"}
        value={`${startTime} ~ ${endTime}`}
        border
      />
      <RaceSpecItem itemKey={"모인 금액"} value={cash * riderCount} border />
      <RaceSpecItem itemKey={"참가자 수"} value={riderCount} border={false} />
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
