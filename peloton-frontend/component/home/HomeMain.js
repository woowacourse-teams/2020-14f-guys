import React from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import RaceList from "./homemain/RaceList";
import RaceListBanner from "./homemain/RaceListBanner";

const HomeMain = () => {
  return (
    <ScrollView style={styles.container}>
      <View style={styles.raceTitle}>
        <RaceListBanner />
      </View>
      <View style={styles.raceList}>
        <RaceList />
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  raceTitle: {
    flex: 1,
    minHeight: 280,
  },
  raceList: {
    flex: 1,
  },
});

export default HomeMain;
