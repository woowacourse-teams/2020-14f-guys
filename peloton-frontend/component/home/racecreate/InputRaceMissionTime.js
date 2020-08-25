import React from "react";
import { Alert } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue } from "recoil";

import RaceCreateUnit from "./RaceCreateUnit";
import RaceCreateView from "./RaceCreateView";
import { RaceCreateUnitType } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import { logNav } from "../../../utils/Analytics";

const InputRaceMissionTime = () => {
  const {
    start_date,
    end_date,
    mission_start_time,
    mission_end_time,
  } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToInputFee = async () => {
    if (!mission_start_time || !mission_end_time) {
      Alert.alert("", "인증 시작, 마감 시간을 선택해주세요");
      return;
    }
    if (mission_start_time === mission_end_time) {
      Alert.alert("", "미션 시작 시간과 마감 시간이 같을 수 없습니다.");
      return;
    }
    if (start_date === end_date && mission_start_time > mission_end_time) {
      Alert.alert(
        "",
        "당일 미션은 인증 시작 시간이 마감 시간보다 빨라야합니다.",
      );
      return;
    }

    const firstMissionDateTime = new Date(
      `${start_date}T${mission_start_time}Z`
    );
    const currentDateTime = new Date();
    if (firstMissionDateTime < currentDateTime) {
      Alert.alert("", "미션 시작 날짜와 시간은 현재시간 이후여야합니다.");
      return;
    }
    logNav("Home", "RaceCreate5(InputRaceMissionDays)");
    navigation.navigate("InputRaceMissionDays");
  };

  return (
    <RaceCreateView onPress={navigateToInputFee}>
      <RaceCreateUnit
        type={RaceCreateUnitType.TIME}
        fieldName="mission_start_time"
      >
        미션 시작 시간을 선택해주세요
      </RaceCreateUnit>
      <RaceCreateUnit
        type={RaceCreateUnitType.TIME}
        fieldName="mission_end_time"
      >
        미션 종료 시간을 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceMissionTime;
