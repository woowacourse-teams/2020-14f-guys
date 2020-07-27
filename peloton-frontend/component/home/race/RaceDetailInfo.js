import React from "react";
import { Image, StyleSheet, Text, View } from "react-native";
import { IMAGE_URL } from "../../../utils/constants";
import ReadMore from "../util/ReadMore";

const RaceDetailInfo = () => {
  return (
    <View style={styles.container}>
      <Image style={styles.image} source={{ uri: IMAGE_URL }} />
      <Text style={styles.achievement}>성취율 90%</Text>
      <Text style={styles.duration}>24시간 15분 32초 남았습니다.</Text>
      <View style={styles.raceInfoText}>
        <Text style={styles.title}>React Native Study</Text>
        <ReadMore>
          React Native를 스터디해서 어플을 출시하고, 사업에 성공하고, 돈을 많이
          벌어서, 집도 사고, 페라리도 사고 행복한 삶을 삽시다. 고양이도 사고,
          마당 있는 집을 사서 개도 몇 마리 기르고 유튜브도 합시다.
        </ReadMore>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    minHeight: 320,
    flex: 1,
    overflow: "hidden",
    backgroundColor: "white",
    borderBottomEndRadius: 15,
    borderBottomStartRadius: 15,
  },
  image: {
    height: 200,
  },
  achievement: {
    position: "absolute",
    left: 30,
    top: 30,
  },
  duration: {
    position: "absolute",
    color: "black",
    right: 30,
    top: 30,
  },
  raceInfoText: {
    paddingHorizontal: 20,
    paddingVertical: 20,
    backgroundColor: "#F2F2F2",
  },
  title: {
    color: "black",
    fontWeight: "900",
    fontSize: 17,
    justifyContent: "center",
    paddingBottom: 10,
  },
});

export default RaceDetailInfo;
