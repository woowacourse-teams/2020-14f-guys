import React from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
} from "react-native";
import axios from "axios";
import { useRecoilValue } from "recoil";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/CreateState";
import { BASE_URL } from "../../../utils/constants";

const InputRaceInfo = () => {
  const {
    title,
    description,
    startDate,
    endDate,
    category,
    entranceFee,
  } = useRecoilValue(raceCreateInfoState);

  const formatInfo = () => {
    return {
      title,
      description,
      category,
      entranceFee,
      raceDuration: {
        startDate,
        endDate,
      },
    };
  };

  const onPress = async () => {
    if (
      !title ||
      !description ||
      !category ||
      !entranceFee ||
      !startDate ||
      !endDate
    ) {
      alert("필드를 모두 채워주세요");
      return;
    }
    try {
      await axios({
        method: "post",
        baseURL: BASE_URL,
        url: "/api/races",
        data: formatInfo(),
      });
    } catch (e) {
      alert("문제가 발생했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <KeyboardAwareScrollView extraHeight={150} style={styles.container}>
        <RaceCreateUnit fieldName="title">제목</RaceCreateUnit>
        <RaceCreateUnit fieldName="description">설명</RaceCreateUnit>
        <RaceCreateUnit date fieldName="startDate">
          시작 날짜
        </RaceCreateUnit>
        <RaceCreateUnit date fieldName="endDate">
          종료 날짜
        </RaceCreateUnit>
        <RaceCreateUnit fieldName="entranceFee">입장료</RaceCreateUnit>
        <TouchableOpacity style={styles.button} onPress={onPress}>
          <Text style={styles.buttonText}>만들기</Text>
        </TouchableOpacity>
      </KeyboardAwareScrollView>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 30,
    paddingBottom: 20,
    paddingHorizontal: 35,
  },
  button: {
    width: 178,
    height: 50,
    borderRadius: 100,
    backgroundColor: "#ffffff",
    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 50,
    shadowOpacity: 1,
    marginBottom: 50,
    alignSelf: "center",
    justifyContent: "center",
    alignItems: "center",
  },
  buttonText: {
    fontSize: 14,
  },
});

export default InputRaceInfo;
