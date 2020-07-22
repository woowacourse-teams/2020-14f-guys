import React from "react";
import { StyleSheet, Text, View } from "react-native";
import RaceItems from "./RaceItems";

const RaceList = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>달리고 있는 레이스들</Text>
      <View style={styles.raceItems}>
        <RaceItems />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 50,
  },
  raceItems: {
    marginTop: 17,
  },
  title: {
    paddingLeft: 35,
    fontSize: 18,
    fontWeight: "600",
  },
});

export default RaceList;
