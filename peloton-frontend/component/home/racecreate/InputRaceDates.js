import React from "react";
import { useRecoilValue } from "recoil";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import RaceCreateView from "./RaceCreateView";
import { RaceCreatUnitType } from "../../../utils/constants";

const InputRaceInfo = () => {
  const { start_date, end_date } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToMissionInput = async () => {
    if (!start_date || !end_date) {
      alert("필드를 모두 채워주세요");
      return;
    }
    if (start_date > end_date) {
      alert("레이스 종료 날짜가 시작 날짜보다 빠릅니다.");
      return;
    }
    navigation.navigate("InputRaceMission");
  };

  return (
    <RaceCreateView onPress={navigateToMissionInput}>
      <RaceCreateUnit type={RaceCreatUnitType.DATE} fieldName="start_date">
        레이스가 시작되는 날짜를 선택해주세요
      </RaceCreateUnit>
      <RaceCreateUnit type={RaceCreatUnitType.DATE} fieldName="end_date">
        레이스가 종료되는 날짜를 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceInfo;
