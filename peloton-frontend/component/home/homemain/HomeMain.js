import React from "react";
import { ScrollView, StyleSheet, View } from "react-native";

import RaceList from "./RaceList";
import HomeMainBanner from "./HomeMainBanner";

const HomeMain = () => {
  return (
    <ScrollView>
      <View style={styles.raceTitle}>
        <HomeMainBanner />
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
