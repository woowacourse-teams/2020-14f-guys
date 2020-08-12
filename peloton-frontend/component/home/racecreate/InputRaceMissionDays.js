import React from "react";
import { useNavigation } from "@react-navigation/native";
import RaceCreateView from "./RaceCreateView";
import RaceCreateUnit from "./RaceCreateUnit";
import { RaceCreatUnitType } from "../../../utils/constants";

const InputRaceMissionDays = () => {
  const navigation = useNavigation();

  const navigateToFeeInput = async () => {
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
