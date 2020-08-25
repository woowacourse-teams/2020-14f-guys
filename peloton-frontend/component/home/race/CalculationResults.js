import React from "react";
import { StyleSheet, View } from "react-native";
import CalculationResult from "./CalculationResult";

const CalculationResults = ({ achievementRates }) => {
  return (
    <View style={styles.container}>
      {achievementRates && achievementRates.length > 0
        ? achievementRates.map((achievementRate, index) => (
            <CalculationResult achievementRate={achievementRate} key={index} />
          ))
        : null}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    margin: 30,
  },
});

export default CalculationResults;
