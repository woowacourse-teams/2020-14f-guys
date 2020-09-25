import React from "react";
import { Image, ScrollView, StyleSheet, Text, TouchableOpacity, } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { useSetRecoilState } from "recoil";

import { raceCreateInfoState } from "../../../state/race/RaceState";
import {
  COLOR,
  RACE_CATEGORY_EXERCISE,
  RACE_CATEGORY_PLAY,
  RACE_CATEGORY_STUDY,
  RACE_CATEGORY_TIME,
} from "../../../utils/constants";
import { logNav } from "../../../utils/Analytics";

const CategorySelection = () => {
  const navigation = useNavigation();
  const setRaceCreateInfo = useSetRecoilState(raceCreateInfoState);

  const onSelectCategory = (category) => {
    setRaceCreateInfo((previousInfo) => ({
      ...previousInfo,
      category,
    }));
    logNav("Home", "RaceCreate2(InputRaceInfo)");
    navigation.navigate("InputRaceInfo");
  };

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.title}>어떤 레이스를 원하세요?</Text>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.item}
        onPress={() => onSelectCategory(RACE_CATEGORY_TIME)}
      >
        <Image
          style={styles.itemImage}
          source={require("../../../assets/race_category_meeting.jpg")}
        />
        <Text style={styles.itemTitle}>모임</Text>
        <Text style={styles.itemSubtitle}>친구들과 생산성을 높여보아요.👩‍💻</Text>
      </TouchableOpacity>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.item}
        onPress={() => onSelectCategory(RACE_CATEGORY_STUDY)}
      >
        <Image
          style={styles.itemImage}
          source={require("../../../assets/race_category_books.jpg")}
        />
        <Text style={styles.itemTitle}>학습</Text>
        <Text style={styles.itemSubtitle}>
          함께 가면 멀리 간대요! 같이 공부해요.📖
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.item}
        onPress={() => onSelectCategory(RACE_CATEGORY_PLAY)}
      >
        <Image
          style={styles.itemImage}
          source={require("../../../assets/race_category_leisure.jpg")}
        />
        <Text style={styles.itemTitle}>여가</Text>
        <Text style={styles.itemSubtitle}>
          쉬는 것도 함께 하면 두배로 행복!👭
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.item}
        onPress={() => onSelectCategory(RACE_CATEGORY_EXERCISE)}
      >
        <Image
          style={styles.itemImage}
          source={require("../../../assets/race_category_exercise.jpg")}
        />
        <Text style={styles.itemTitle}>운동</Text>
        <Text style={styles.itemSubtitle}>
          힘든 운동도 함께라면 할 수 있어요!🏋️‍♂️
        </Text>
      </TouchableOpacity>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE,
    paddingHorizontal: 15,
  },
  title: {
    marginVertical: 20,
    fontSize: 18,
    fontWeight: "600",
  },
  item: {
    minWidth: 320,
    width: "100%",
    aspectRatio: 32 / 21,
    borderRadius: 10,
    overflow: "hidden",
    marginBottom: 20,
  },
  itemImage: {
    width: 400,
    height: 250,
    resizeMode: "cover",
  },
  itemTitle: {
    position: "absolute",
    left: 19.5,
    bottom: 49,
    color: COLOR.WHITE,
    fontWeight: "bold",
    fontSize: 30,
    paddingBottom: 10,
  },
  itemSubtitle: {
    position: "absolute",
    left: 19.5,
    bottom: 30,
    color: COLOR.WHITE,
    fontSize: 15,
  },
});

export default CategorySelection;
