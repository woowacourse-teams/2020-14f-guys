import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import axios from "axios";
import { useRecoilValue } from "recoil";

import RaceCreateInputBox from "./RaceCreateInputBox";
import { raceCreateInfoState } from "../../../state/race/CreateState";

const InputRaceInfo = () => {
  const raceCreateInfo = useRecoilValue(raceCreateInfoState);

  const formatInfo = () => {
    return {
      title: raceCreateInfo.title,
      description: raceCreateInfo.description,
      raceDuration: {
        startDate: raceCreateInfo.startDate,
        endDate: raceCreateInfo.endDate,
      },
      category: raceCreateInfo.category,
      entranceFee: {
        cash: raceCreateInfo.entranceFee,
      },
    };
  };

  const onPress = async () => {
    const response = await axios({
      method: "post",
      baseURL: "http://localhost:8080",
      url: "/api/races",
      data: formatInfo(),
    });
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
