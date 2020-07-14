import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { SAMPLE_SUBTITLE } from "../../../utils/constants";

const RaceListTitle = (props) => {
  const subtitle =
    SAMPLE_SUBTITLE[Math.floor(Math.random() * SAMPLE_SUBTITLE.length)];
  return (
    <View style={styles.container}>
      <Text style={styles.title}>블비씨,</Text>
      <Text style={styles.subtitle}>{subtitle}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "flex-start",
    paddingTop: 35,
    paddingLeft: 20,
  },
  title: {
    fontSize: 33,
    fontWeight: "300",
    color: "#595959",
    marginBottom: 5,
  },
  subtitle: {
    fontSize: 20,
    fontWeight: "300",
    color: "#595959",
    marginBottom: 10,
  },
});

export default RaceListTitle;
