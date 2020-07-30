import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";

const CustomButton = ({ text, onPress }) => {
  return (
    <View style={styles.buttonContainer}>
      <TouchableOpacity style={styles.button} onPress={onPress}>
        <Text style={styles.buttonText}>{text}</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  buttonContainer: {
    flex: 2.5,
    marginBottom: 30,
  },
  button: {
    borderWidth: 1,
    width: 100,
    height: 28,
    borderColor: "#F2F2F2",
    borderRadius: 100,
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonText: {
    color: "#F2F2F2",
    fontSize: 14,
  },
});

export default CustomButton;
