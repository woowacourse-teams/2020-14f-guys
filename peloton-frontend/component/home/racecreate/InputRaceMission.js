import React from "react";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import RaceCreateView from "./RaceCreateView";
import { RaceCreatUnitType } from "../../../utils/constants";

const InputRaceMission = () => {
  // eslint-disable-next-line prettier/prettier
  const navigation = useNavigation();

  const navigateToFeeInput = async () => {
    navigation.navigate("InputRaceFee");
  };

  return (
    <RaceCreateView onPress={navigateToFeeInput}>
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

export default InputRaceMission;
