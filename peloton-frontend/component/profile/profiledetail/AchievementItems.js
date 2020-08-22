import React, { useEffect } from "react";
import { ScrollView, StyleSheet, Text, View } from "react-native";
import AchievementItem from "./AchievementItem";
import { COLOR } from "../../../utils/constants";

const AchievementItems = () => {
  useEffect(() => {}, []);

  return (
    <View>
      <Text style={styles.title}>나의 성취율</Text>
      <ScrollView horizontal={true} contentContainerStyle={styles.container}>
        <AchievementItem ratio={100} raceInitial={"즐"} color={COLOR.PURPLE} />
        <AchievementItem ratio={50} raceInitial={"지"} color={COLOR.LAVENDER} />
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginHorizontal: 20,
    marginVertical: 10,
  },
  title: {
    paddingTop: 35,
    paddingLeft: 50,
    fontSize: 18,
    fontWeight: "600",
  },
});

export default AchievementItems;
