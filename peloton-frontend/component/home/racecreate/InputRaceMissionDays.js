import React from "react";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue } from "recoil";

import RaceCreateView from "./RaceCreateView";
import RaceCreateUnit from "./RaceCreateUnit";
import { RaceCreateUnitType } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/RaceState";

const InputRaceMissionDays = () => {
  const { days } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToMissionTime = async () => {
    if (days.length === 0) {
      alert("요일을 하나 이상 선택해주세요");
      return;
    }
    navigation.navigate("InputRaceMissionTime");
  };

  return (
    <RaceCreateView onPress={navigateToMissionTime}>
      <RaceCreateUnit type={RaceCreateUnitType.DAYS} fieldName="days">
        미션이 진행될 요일을 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceMissionDays;
