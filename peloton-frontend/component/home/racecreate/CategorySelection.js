import React from "react";
import {
  Dimensions,
  FlatList,
  Image,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { useNavigation } from "@react-navigation/native";
import { useSetRecoilState } from "recoil";

import { raceCreateInfoState } from "../../../state/race/RaceState";
import { CATEGORY, COLOR } from "../../../utils/constants";
import CategoryItem from "./CategoryItem";

const CategorySelection = () => {
  const navigation = useNavigation();
  const setRaceCreateInfo = useSetRecoilState(raceCreateInfoState);

  const onSelectCategory = (category) => {
    setRaceCreateInfo((previousInfo) => ({
      ...previousInfo,
      category,
    }));
    navigation.navigate("InputRaceInfo");
  };

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.title}>어떤 레이스를 원하세요?</Text>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.item}
        onPress={() => onSelectCategory("TIME")}
      >
        <Image
          style={styles.itemImage}
          source={require("../../../assets/race_category_1.jpg")}
        />
        <Text style={styles.itemTitle}>모임</Text>
        <Text style={styles.itemSubtitle}>Ice Breaking</Text>
      </TouchableOpacity>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.item}
        onPress={() => onSelectCategory("STUDY")}
      >
        <Image
          style={styles.itemImage}
          source={require("../../../assets/race_category_2.jpg")}
        />
        <Text style={styles.itemTitle}>학습</Text>
        <Text style={styles.itemSubtitle}>Learning</Text>
      </TouchableOpacity>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.item}
        onPress={() => onSelectCategory("PLAY")}
      >
        <Image
          style={styles.itemImage}
          source={require("../../../assets/race_category_3.jpg")}
        />
        <Text style={styles.itemTitle}>여가</Text>
        <Text style={styles.itemSubtitle}>Play</Text>
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
