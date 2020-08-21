import React from "react";
import { StyleSheet, View } from "react-native";
import CalculationResult from "./CalculationResult";

const CalculationResults = ({ calculations }) => {
  return (
    <View style={styles.container}>
      {calculations.map((calculation, index) => (
        <CalculationResult calculation={calculation} key={index} />
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
