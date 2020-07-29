import React from "react";
import { ScrollView, StyleSheet, View } from "react-native";

import RaceList from "./RaceList";
import HomeBanner from "./HomeBanner";

const Home = () => {
  return (
    <ScrollView>
      <View style={styles.raceTitle}>
        <HomeBanner />
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

export default Home;
