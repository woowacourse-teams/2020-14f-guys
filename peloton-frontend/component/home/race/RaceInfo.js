import React from "react";
import { Image, Text, View, StyleSheet } from "react-native";
import { IMAGE_URL } from "../../Const";

const RaceInfo = () => {
  return (
    <View style={styles.raceInfoContainer}>
      <View style={styles.cardContainer}>
        <Image style={styles.image} source={{ uri: IMAGE_URL }} />
        <Text style={styles.achievement}>성취률 90%</Text>
        <Text style={styles.duration}>24시간 15분 32초 남았습니다.</Text>
        <View style={styles.textContainer}>
          <Text style={styles.title}>React Native Study</Text>
          <Text style={styles.description}>
            React Native를 학습하여 모바일 개발자로 거듭나기 위한 스터디입니다.
            열심히 공부해서 잘해봅시다!
          </Text>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  raceInfoContainer: {
    height: 375,
    paddingHorizontal: 30,
    paddingVertical: 30,
  },
  cardContainer: {
    flex: 1,
    overflow: "hidden",
    borderRadius: 15,
    backgroundColor: "white",
  },
  image: {
    height: 200,
  },
  achievement: {
    position: "absolute",
    color: "black",
    left: 30,
    top: 30,
  },
  duration: {
    position: "absolute",
    color: "black",
    right: 30,
    top: 30,
  },
  textContainer: {
    paddingHorizontal: 10,
    paddingVertical: 10,
  },
  title: {
    color: "black",
    fontWeight: "900",
    fontSize: 17,
    justifyContent: "center",
    paddingBottom: 10,
  },
  description: {
    color: "gray",
    fontSize: 15,
    fontWeight: "300",
  },
});

export default RaceInfo;
