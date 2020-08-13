import React from "react";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { useRecoilValue } from "recoil";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import RaceCreateView from "./RaceCreateView";

const InputRaceInfo = () => {
  const navigation = useNavigation();
  const { title, description } = useRecoilValue(raceCreateInfoState);

  const navigateToDateInput = async () => {
    if (!title || !description) {
      alert("필드를 모두 채워주세요");
      return;
    }
    navigation.navigate("InputRaceDates");
  };

  return (
    <RaceCreateView onPress={navigateToDateInput}>
      <RaceCreateUnit fieldName="title">
        Race의 이름을 입력해주세요
      </RaceCreateUnit>
      <RaceCreateUnit fieldName="description">
        레이스에 대해 설명해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceInfo;
