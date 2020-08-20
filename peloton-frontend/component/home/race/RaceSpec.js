import React from "react";
import { StyleSheet, Text, View } from "react-native";
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
      <Text style={styles.title}>레이스 정보 확인하기</Text>
      <RaceSpecItem
        itemKey={"레이스 기간"}
        value={`${startDate} ~ ${endDate}`}
        border
      />
      <RaceSpecItem itemKey={"인증 주기"} value={days} border />
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
    paddingTop: 10,
    paddingBottom: 10,
    paddingHorizontal: 20,
    backgroundColor: COLOR.GRAY6,
  },
  title: {
    fontSize: 25,
    paddingBottom: 30,
    fontWeight: "400",
  },
  border: {
    borderWidth: 1,
    borderColor: COLOR.GRAY5,
    marginTop: 10,
    marginBottom: 25,
  },
});

export default RaceSpec;
