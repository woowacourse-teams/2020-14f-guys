import React from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { useRecoilValue } from "recoil";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/CreateState";
import RaceCreateView from "./RaceCreateView";

const InputRaceInfo = () => {
  const { startDate, endDate } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const onPress = async () => {
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
    <RaceCreateView onPress={onPress}>
      <RaceCreateUnit date fieldName="startDate">
        레이스가 시작되는 날짜를 선택해주세요
      </RaceCreateUnit>
      <RaceCreateUnit date fieldName="endDate">
        레이스가 종료되는 날짜를 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

const styles = StyleSheet.create({});

export default InputRaceInfo;
