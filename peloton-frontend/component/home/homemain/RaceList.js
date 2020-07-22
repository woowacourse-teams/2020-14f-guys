import React from "react";
import { StyleSheet, View } from "react-native";
import RaceListBanner from "./RaceListBanner";
import RaceItems from "./RaceItems";

const RaceList = () => {
  return (
    <View style={styles.container}>
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
  raceItems: {
    flex: 5,
  },
});

export default RaceList;
