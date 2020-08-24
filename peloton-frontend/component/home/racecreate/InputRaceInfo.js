import React from "react";
import { Alert } from "react-native";
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
      Alert.alert("", "이름과 설명을 모두 입력해주세요");
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
