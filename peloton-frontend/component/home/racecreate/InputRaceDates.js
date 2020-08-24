import React from "react";
import { Alert } from "react-native";
import { useRecoilValue } from "recoil";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import RaceCreateView from "./RaceCreateView";
import { RaceCreateUnitType } from "../../../utils/constants";

const InputRaceDates = () => {
  const { start_date, end_date } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToMissionDays = async () => {
    if (!start_date || !end_date) {
      Alert.alert("", "시작 날짜와 종료 날짜를 모두 선택해주세요");
      return;
    }
    if (start_date > end_date) {
      Alert.alert("", "레이스 종료 날짜가 시작 날짜보다 빠릅니다");
      return;
    }
    navigation.navigate("InputRaceMissionTime");
  };

  return (
    <RaceCreateView onPress={navigateToMissionDays}>
      <RaceCreateUnit type={RaceCreateUnitType.DATE} fieldName="start_date">
        레이스가 시작되는 날짜를 선택해주세요
      </RaceCreateUnit>
      <RaceCreateUnit type={RaceCreateUnitType.DATE} fieldName="end_date">
        레이스가 종료되는 날짜를 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceDates;
