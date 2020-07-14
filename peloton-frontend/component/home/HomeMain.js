import React from "react";
import { ScrollView, StyleSheet } from "react-native";
import RaceList from "./racelist/RaceList";

const HomeMain = () => {
  return (
    <ScrollView style={styles.container}>
      <RaceList />
      <RaceList />
    </ScrollView>
  );
};

export default HomeMain;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
});
