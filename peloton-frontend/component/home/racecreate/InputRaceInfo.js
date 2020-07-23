import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import axios from "axios";
import { useRecoilValue } from "recoil";

import RaceCreateInputBox from "./RaceCreateInputBox";
import { raceCreateInfoState } from "../../../state/race/CreateState";

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
        baseURL: "https://c79b7070ce58.ngrok.io",
        url: "/api/races",
        data: formatInfo(),
      });
    } catch (e) {
      alert("문제가 발생했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <View style={styles.container}>
      <RaceCreateInputBox fieldName="title">제목</RaceCreateInputBox>
      <RaceCreateInputBox fieldName="description">설명</RaceCreateInputBox>
      <RaceCreateInputBox fieldName="startDate">시작 날짜</RaceCreateInputBox>
      <RaceCreateInputBox fieldName="endDate">종료 날짜</RaceCreateInputBox>
      <RaceCreateInputBox fieldName="entranceFee">입장료</RaceCreateInputBox>
      <TouchableOpacity onPress={onPress}>
        <Text>만들기</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    paddingTop: 30,
  },
});

export default InputRaceInfo;
