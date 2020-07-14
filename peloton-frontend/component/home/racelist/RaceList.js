import React from "react";
import { StyleSheet, View } from "react-native";
import RaceListTitle from "./RaceListTitle";
import RaceItems from "./RaceItems";

const RaceList = (props) => {
  return (
    <View style={styles.container}>
      <RaceListTitle />
      <RaceItems />
    </View>
  );
};

export default RaceList;

const styles = StyleSheet.create({
  container: {
    flex: 6,
  },
});
