import React from "react";
import { StyleSheet, View } from "react-native";
import CalculationResult from "./CalculationResult";

const CalculationResults = ({ achievementRates }) => {
  return (
    <View style={styles.container}>
      {achievementRates.map((achievementRate, index) => (
        <CalculationResult achievementRate={achievementRate} key={index} />
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    margin: 40,
  },
});

export default CalculationResults;
