import React from "react";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue } from "recoil";

import RaceCreateView from "./RaceCreateView";
import RaceCreateUnit from "./RaceCreateUnit";
import { RaceCreatUnitType } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/CreateState";

const InputRaceMissionDays = () => {
  const { days } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToFeeInput = async () => {
    if (days.length === 0) {
      alert("요일을 하나 이상 선택해주세요");
      return;
    }
    navigation.navigate("InputRaceFee");
  };

  return (
    <RaceCreateView onPress={navigateToFeeInput}>
      <RaceCreateUnit type={RaceCreatUnitType.DAYS} fieldName="days">
        미션이 진행될 요일을 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceMissionDays;
