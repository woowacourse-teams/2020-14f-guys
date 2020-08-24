import React from "react";
import { Alert } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue } from "recoil";

import RaceCreateView from "./RaceCreateView";
import RaceCreateUnit from "./RaceCreateUnit";
import { RaceCreateUnitType } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/RaceState";

const InputRaceMissionDays = () => {
  const { days } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToMissionTime = () => {
    if (days.length === 0) {
      Alert.alert("", "요일을 하나 이상 선택해주세요");
      return;
    }
    navigation.navigate("InputRaceFee");
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
