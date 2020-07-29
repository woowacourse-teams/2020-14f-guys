import React from "react";
import { StyleSheet, TouchableOpacity, View } from "react-native";
import { MaterialIcons } from "@expo/vector-icons";

const SubmitButton = ({ onSubmit }) => {
  return (
    <View style={styles.nextButtonContainer}>
      <TouchableOpacity onPress={onSubmit}>
        <MaterialIcons name="navigate-next" size={36} color="#61779f" />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  nextButtonContainer: {
    marginTop: 20,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#284170",
    borderRadius: 40,
    width: 50,
    height: 50,
  },
});

export default SubmitButton;
