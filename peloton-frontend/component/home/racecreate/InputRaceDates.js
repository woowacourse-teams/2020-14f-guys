import React from "react";
import { useRecoilValue } from "recoil";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/CreateState";
import RaceCreateView from "./RaceCreateView";

const InputRaceInfo = () => {
  const { startDate, endDate } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToFeeInput = async () => {
    if (!startDate || !endDate) {
      alert("필드를 모두 채워주세요");
      return;
    }
    if (startDate > endDate) {
      alert("레이스 종료 날짜가 시작 날짜보다 빠릅니다.");
      return;
    }
    navigation.navigate("InputRaceFee");
  };

  return (
    <RaceCreateView onPress={navigateToFeeInput}>
      <RaceCreateUnit date fieldName="startDate">
        레이스가 시작되는 날짜를 선택해주세요
      </RaceCreateUnit>
      <RaceCreateUnit date fieldName="endDate">
        레이스가 종료되는 날짜를 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceInfo;
