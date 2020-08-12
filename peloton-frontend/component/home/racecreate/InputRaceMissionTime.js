import React from "react";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue } from "recoil";

import RaceCreateUnit from "./RaceCreateUnit";
import RaceCreateView from "./RaceCreateView";
import { RaceCreatUnitType } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/CreateState";

const InputRaceMissionTime = () => {
  const { mission_start_time, mission_end_time } = useRecoilValue(
    raceCreateInfoState,
  );
  const navigation = useNavigation();

  const navigateToInputDays = async () => {
    if (!mission_start_time || !mission_end_time) {
      alert("미션 시작, 종료 시간을 선택해주세요");
      return;
    }
    if (mission_start_time > mission_end_time) {
      alert("미션 시작 시간이 종료 시간보다 빨라야합니다.");
      return;
    }
    navigation.navigate("InputRaceMissionDays");
  };

  return (
    <RaceCreateView onPress={navigateToInputDays}>
      <RaceCreateUnit
        type={RaceCreatUnitType.TIME}
        fieldName="mission_start_time"
      >
        미션 시작 시간을 선택해주세요
      </RaceCreateUnit>
      <RaceCreateUnit
        type={RaceCreatUnitType.TIME}
        fieldName="mission_end_time"
      >
        미션 종료 시간을 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceMissionTime;
