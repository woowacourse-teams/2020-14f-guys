import React from "react";
import { StyleSheet, View } from "react-native";
import RaceListTitle from "./RaceListTitle";
import RaceItems from "./RaceItems";

const RaceList = () => {
  return (
    <View style={styles.container}>
      <View style={styles.raceListTitle}>
        <RaceListTitle />
      </View>
      <View style={styles.raceItems}>
        <RaceItems />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  raceListTitle: {
    flex: 1,
  },
  raceItems: {
    flex: 5,
  },
});

export default RaceList;
