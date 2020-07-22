import React from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import RaceList from "./homemain/RaceList";

const HomeMain = () => {
  return (
    <ScrollView style={styles.container}>
      <View style={styles.raceList}>
        <RaceList />
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
  raceList: {
    flex: 1,
  },
});

export default HomeMain;
