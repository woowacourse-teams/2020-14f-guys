import React from "react";
import { StyleSheet, Text, View } from "react-native";

const RaceItemText = ({ item }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.raceTitle}>{item.title}</Text>
      <Text style={styles.raceDescription} numberOfLines={1}>
        {item.description}
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 2,
    justifyContent: "flex-start",
    alignItems: "flex-start",
    paddingTop: 10,
    paddingHorizontal: 15,
    paddingBottom: 10,
  },
  raceTitle: {
    fontSize: 15,
    fontWeight: "bold",
    paddingBottom: 10,
  },
  raceDescription: {
    fontSize: 12,
  },
});

export default RaceItemText;
